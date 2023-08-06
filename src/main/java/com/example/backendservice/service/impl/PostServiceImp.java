package com.example.backendservice.service.impl;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMediaService postMediaService;
    private final PostMapper postMapper;
    private final UploadFileUtil uploadFileUtil;
    @Override
    public PostDto createNewPost(PostCreateDto postCreateDto, String userId) {
        if ((postCreateDto.getCaption() == null || postCreateDto.getCaption().equals("")) &&
                (postCreateDto.getFiles() == null || postCreateDto.getFiles().isEmpty())) {
            throw new InvalidException(ErrorMessage.Post.ERR_INVALID);
        }
        else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
            Post post = postMapper.postCreateDtoToPost(postCreateDto);
            post.setUser(user);
            postRepository.save(post);
            List<PostMedia> postMediaList = new ArrayList<>();
            if (postCreateDto.getFiles() != null && !postCreateDto.getFiles().isEmpty()) {
                for (MultipartFile file : postCreateDto.getFiles()) {
                    postMediaList.add(postMediaService.createNewPostMedia(post.getId(), file));
                }
            }
            post.setPostMedia(postMediaList);
            return postMapper.postToPostDto(postRepository.save(post));
        }
    }

    @Override
    public PostDto getPostById(String postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        return postMapper.postToPostDto(post);
    }

    @Override
    public PostDto updatePostById(String postId, PostUpdateDto postUpdateDto, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));

        if (post.getUser().getId().equals(userId)) {
            if ((postUpdateDto.getCaption() == null || postUpdateDto.getCaption().equals("")) &&
                    (postUpdateDto.getFiles() == null || postUpdateDto.getFiles().isEmpty())) {
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
                if (postUpdateDto.getFiles() != null && !postUpdateDto.getFiles().isEmpty()) {
                    for (MultipartFile file : postUpdateDto.getFiles()) {
                        postMediaList.add(postMediaService.createNewPostMedia(post.getId(), file));
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
    public CommonResponseDto deletePostById(String postId, String userId) {
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
            return new CommonResponseDto(true, MessageConstant.DELETE_POST_SUCCESSFULLY);
        }
        else {
            throw new UnauthorizedException(ErrorMessage.Post.ERR_UNAUTHORIZED);
        }
    }

    @Override
    public List<PostDto> findAllPostByUserId(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        List<Post> posts = postRepository.findAllPostByUserId(userId);
        return postMapper.postsToPostDtos(posts);
    }

    @Override
    public List<PostDto> findAllPostByUsername(String username) {
        userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME, new String[]{username}));
        List<Post> posts = postRepository.findAllPostByUsername(username);
        return postMapper.postsToPostDtos(posts);
    }

    @Override
    public List<PostDto> findAllPost(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        List<Post> posts = postRepository.findAllPost(userId);
        return postMapper.postsToPostDtos(posts);
    }

    @Override
    public List<PostDto> findAllPostMyReaction(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        List<Post> posts = postRepository.findAllPostMyReaction(userId);
        return postMapper.postsToPostDtos(posts);
    }
}
