package com.example.backendservice.service;

import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.domain.dto.response.PostDto;
import com.example.backendservice.domain.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {
    PostDto createNewPost(PostCreateDto postCreateDto, MultipartFile[] multipartFiles, String userId);
    Post getPostById(String postId);
    PostDto updatePostById(String postId, PostUpdateDto postUpdateDto, MultipartFile[] multipartFiles, String userId);
    String deletePostById(String postId,String userId);
    List<PostDto> findAllPostByUserId(String userId);
    List<PostDto> findAllPost();
}
