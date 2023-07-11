package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class PostController {
    private final PostService postService;


    @Tag(name = "post-controller")
    @Operation(summary = "API create new post")
    @PostMapping( value = UrlConstant.Post.CREATE_POST)
    public ResponseEntity<?> createNewPost(@Valid @ModelAttribute PostCreateDto postCreateDto,
                                           @Parameter(name = "user", hidden = true)
                                           @CurrentUser UserPrincipal user    ) {
        return VsResponseUtil.success(postService.createNewPost(postCreateDto, user.getId() ));
    }
    @Tag(name = "post-controller")
    @Operation(summary = "API get post")
    @GetMapping( value = UrlConstant.Post.GET_POST)
    public ResponseEntity<?> getPostById(@PathVariable String postId) {
        return VsResponseUtil.success(postService.getPostById(postId));
    }
    @Tag(name = "post-controller")
    @Operation(summary = "API update post")
    @PatchMapping( value = UrlConstant.Post.UPDATE_POST)
    public ResponseEntity<?> updatePostById(@PathVariable String postId,
                                            @Valid @ModelAttribute PostUpdateDto postUpdateDto,
                                            @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user    ) {
        return VsResponseUtil.success(postService.updatePostById( postId, postUpdateDto, user.getId()));
    }

    @Tag(name = "post-controller")
    @Operation(summary = "API delete post")
    @DeleteMapping( value = UrlConstant.Post.DELETE_POST)
    public ResponseEntity<?> deletePostById(@PathVariable String postId,
                                            @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(postService.deletePostById(postId,user.getId()));
    }

    @Tag(name = "post-controller")
    @Operation(summary = "API get all post by user id")
    @GetMapping( value = UrlConstant.Post.GET_MY_POST)
    public ResponseEntity<?> findAllPostByUserId(@PathVariable String userId) {
        return VsResponseUtil.success(postService.findAllPostByUserId(userId));
    }

    @Tag(name = "post-controller")
    @Operation(summary = "API get all post follow")
    @GetMapping( value = UrlConstant.Post.GET_ALL_POST)
    public ResponseEntity<?> findAllPost(@Parameter(name = "user", hidden = true)
                                             @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(postService.findAllPost(user.getId()));
    }

}
