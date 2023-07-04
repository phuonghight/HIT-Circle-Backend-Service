package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostMediaCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.domain.dto.response.PostDto;
import com.example.backendservice.domain.entity.Post;
import com.example.backendservice.domain.entity.PostMedia;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.PostMapper;
import com.example.backendservice.exception.InvalidException;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.PostRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.service.PostMediaService;
import com.example.backendservice.service.PostService;
import com.example.backendservice.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMediaService postMediaService;
    private final PostMapper postMapper;
    private final UploadFileUtil uploadFileUtil;
    @Override
    public PostDto createNewPost(PostCreateDto postCreateDto, MultipartFile[] multipartFiles, String userId) {
        if ((postCreateDto.getCaption() == null || postCreateDto.getCaption().equals("")) && multipartFiles == null) {
            throw new InvalidException(ErrorMessage.Post.ERR_INVALID);
        }
        else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
            Post post = postMapper.postCreateDtoToPost(postCreateDto);
            post.setUser(user);
            postRepository.save(post);
            List<PostMedia> postMediaList = new ArrayList<>();
            if (multipartFiles != null) {
                for (MultipartFile file : multipartFiles) {
                    PostMediaCreateDto postMediaCreateDto = new PostMediaCreateDto(file);
                    postMediaList.add(postMediaService.createNewPostMedia(post.getId(), postMediaCreateDto));
                }
            }
            post.setPostMedia(postMediaList);
            return postMapper.postToPostDto(postRepository.save(post));
        }
    }

    @Override
    public Post getPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
    }

    @Override
    public PostDto updatePostById(String postId, PostUpdateDto postUpdateDto, MultipartFile[] multipartFiles, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));

        if (post.getUser().getId().equals(userId)) {
            if ((postUpdateDto.getCaption() == null || postUpdateDto.getCaption().equals("")) && multipartFiles == null) {
                throw new InvalidException(ErrorMessage.Post.ERR_INVALID);
            }
            else {
                // Remove file old
                List<PostMedia> oldPostMedias = post.getPostMedia();
                if (!oldPostMedias.isEmpty()) {
                    postMediaService.deletePostMediaByPostId(post.getId());
                }
                post.setPostMedia(null);
                postRepository.save(post);

                postMapper.updatePost(post,postUpdateDto);

                // Add new file
                List<PostMedia> postMediaList = new ArrayList<>();
                if (multipartFiles != null) {
                    for (MultipartFile file : multipartFiles) {
                        PostMediaCreateDto postMediaCreateDto = new PostMediaCreateDto(file);
                        postMediaList.add(postMediaService.createNewPostMedia(post.getId(), postMediaCreateDto));
                    }
                }
                post.setPostMedia(postMediaList);

                return postMapper.postToPostDto(postRepository.save(post));
            }
        }
        else {
            throw new UnauthorizedException(ErrorMessage.Post.ERR_UNAUTHORIZED);
        }

    }

    @Override
    public String deletePostById(String postId,String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        if (post.getUser().getId().equals(userId)) {
            List<PostMedia> postMedias = post.getPostMedia();
            if (!postMedias.isEmpty()) {
                for (PostMedia postMedia : postMedias) {
                    uploadFileUtil.destroyFileWithUrl(postMedia.getMediaFile());
                }
            }
            postRepository.deleteById(postId);
            return "Delete post successful";
        }
        else {
            throw new UnauthorizedException(ErrorMessage.Post.ERR_UNAUTHORIZED);
        }
    }

    @Override
    public List<PostDto> findAllPostByUserId(String userId) {
        List<Post> posts = postRepository.findAllPostByUserId(userId);
        return postMapper.postsToPostDtos(posts);
    }

    @Override
    public List<PostDto> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return postMapper.postsToPostDtos(posts);
    }
}
