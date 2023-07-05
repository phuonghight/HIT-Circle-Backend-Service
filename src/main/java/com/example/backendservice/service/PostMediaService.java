package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.PostMediaCreateDto;
import com.example.backendservice.domain.dto.response.PostMediaDto;
import com.example.backendservice.domain.entity.PostMedia;

import java.util.List;

public interface PostMediaService {
    PostMedia createNewPostMedia(String postId, PostMediaCreateDto postMediaCreateDto);
    void deletePostMediaById(String postMediaId);
    void deletePostMediaByPostId(String postId);
    PostMediaDto getPostMediaById(String postMediaId);
    List<PostMedia> getAllPostMediaByPostId(String postId);
}
