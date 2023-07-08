package com.example.backendservice.service;

import com.example.backendservice.domain.dto.response.PostMediaDto;
import com.example.backendservice.domain.entity.PostMedia;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostMediaService {
    PostMedia createNewPostMedia(String postId, MultipartFile mediaFile);
    void deletePostMediaById(String postMediaId);
    void deletePostMediaByPostId(String postId);
    PostMediaDto getPostMediaById(String postMediaId);
    List<PostMedia> getAllPostMediaByPostId(String postId);
}
