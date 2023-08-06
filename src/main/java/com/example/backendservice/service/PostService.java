package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.PostDto;
import com.example.backendservice.domain.entity.Post;

import java.util.List;

public interface PostService {
    PostDto createNewPost(PostCreateDto postCreateDto, String userId);
    PostDto getPostById(String postId);
    PostDto updatePostById(String postId, PostUpdateDto postUpdateDto, String userId);
    CommonResponseDto deletePostById(String postId, String userId);
    List<PostDto> findAllPostByUserId(String userId);
    List<PostDto> findAllPostByUsername(String username);
    List<PostDto> findAllPost(String userId);
    List<PostDto> findAllPostMyReaction(String userId);
}
