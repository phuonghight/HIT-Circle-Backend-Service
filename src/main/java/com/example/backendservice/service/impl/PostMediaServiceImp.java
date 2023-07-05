package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.dto.request.PostMediaCreateDto;
import com.example.backendservice.domain.dto.response.PostMediaDto;
import com.example.backendservice.domain.entity.Post;
import com.example.backendservice.domain.entity.PostMedia;
import com.example.backendservice.domain.mapper.PostMediaMapper;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.PostMediaRepository;
import com.example.backendservice.repository.PostRepository;
import com.example.backendservice.service.PostMediaService;
import com.example.backendservice.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostMediaServiceImp implements PostMediaService {
    private final PostRepository postRepository;
    private final PostMediaMapper postMediaMapper;

    private final UploadFileUtil uploadFileUtil;
    private final PostMediaRepository postMediaRepository;

    @Override
    public PostMedia createNewPostMedia(String postId, PostMediaCreateDto postMediaCreateDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        PostMedia postMedia = new PostMedia();
        postMedia.setMediaFile(uploadFileUtil.uploadFile(postMediaCreateDto.getMediaFile()));
        postMedia.setPost(post);
        return postMediaRepository.save(postMedia);
    }

    @Override
    public void deletePostMediaById(String postMediaId) {
        PostMedia postMedia = postMediaRepository.findById(postMediaId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PostMedia.ERR_NOT_FOUND_ID, new String[]{postMediaId}));
        uploadFileUtil.destroyFileWithUrl(postMedia.getMediaFile());
        postMediaRepository.delete(postMedia);
    }

    @Override
    public void deletePostMediaByPostId(String postId) {
        List<PostMedia> postMediaList = postMediaRepository.findAllPostMediaByPostId(postId);
        if (!postMediaList.isEmpty()) {
            for (PostMedia postMedia : postMediaList) {
                uploadFileUtil.destroyFileWithUrl(postMedia.getMediaFile());
            }
        }
        postMediaRepository.deletePosMediaByPostId(postId);
    }

    @Override
    public PostMediaDto getPostMediaById(String postMediaId) {
        PostMedia postMedia = postMediaRepository.findById(postMediaId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.PostMedia.ERR_NOT_FOUND_ID, new String[]{postMediaId}));
        return postMediaMapper.postMediaToPostMediaDto(postMedia);
    }

    @Override
    public List<PostMedia> getAllPostMediaByPostId(String postId) {
        return postMediaRepository.findAllPostMediaByPostId(postId);
    }
}
