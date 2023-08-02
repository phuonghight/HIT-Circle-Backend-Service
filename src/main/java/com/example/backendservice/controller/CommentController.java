package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.request.CommentCreateDto;
import com.example.backendservice.domain.dto.request.CommentUpdateDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class CommentController {
    private final CommentService commentService;

    @Tag(name = "comment-controller")
    @Operation(summary = "API create new comment")
    @PostMapping(value = UrlConstant.Comment.CREATE_COMMENT)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> sendComment(@Valid @RequestBody CommentCreateDto commentCreateDto,
                                                    @Parameter(name = "user", hidden = true)
                                                    @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(commentService.sendComment(user.getId(), commentCreateDto));
    }

    @Tag(name = "comment-controller")
    @Operation(summary = "API update comment")
    @PatchMapping(value = UrlConstant.Comment.UPDATE_COMMENT)
    public ResponseEntity<?> updateCommentById(@Valid @RequestBody CommentUpdateDto commentUpdateDto,
                                         @PathVariable String commentId,
                                         @Parameter(name = "user", hidden = true)
                                         @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(commentService.updateComment(user.getId(), commentId, commentUpdateDto));
    }

    @Tag(name = "comment-controller")
    @Operation(summary = "API delete comment")
    @DeleteMapping(value = UrlConstant.Comment.DELETE_COMMENT)
    public ResponseEntity<?> deleteCommentById(@PathVariable String commentId,
                                               @Parameter(name = "user", hidden = true)
                                               @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(commentService.deleteComment(user.getId(), commentId));
    }

    @Tag(name = "comment-controller")
    @Operation(summary = "API get comment by id")
    @GetMapping(value = UrlConstant.Comment.GET_BY_COMMENT_ID)
    public ResponseEntity<?> getCommentById(@PathVariable String commentId) {
        return VsResponseUtil.success(commentService.getCommentById(commentId));
    }

    @Tag(name = "comment-controller")
    @Operation(summary = "API get all comment lv1 by post id")
    @GetMapping(value = UrlConstant.Comment.GET_ALL_BY_POST_ID)
    public ResponseEntity<?> getAllCommentByPostId(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                               @PathVariable String postId) {
        return VsResponseUtil.success(commentService.getAllCommentByPostId(paginationFullRequestDto, postId));
    }

    @Tag(name = "comment-controller")
    @Operation(summary = "API get all reply comment by parent comment id")
    @GetMapping(value = UrlConstant.Comment.GET_ALL_BY_PARENT_COMMENT_ID)
    public ResponseEntity<?> getAllReplyCommentByParentCommentId(@Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto,
                                                   @PathVariable String parentCommentId) {
        return VsResponseUtil.success(commentService.getAllReplyByParentCommentId(paginationFullRequestDto, parentCommentId));
    }
}
