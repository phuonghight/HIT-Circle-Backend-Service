package com.example.backendservice.service;

import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.request.CommentCreateDto;
import com.example.backendservice.domain.dto.request.CommentUpdateDto;
import com.example.backendservice.domain.dto.response.CommentResponseDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;

public interface CommentService {
    CommentResponseDto sendComment(String userId, CommentCreateDto commentCreateDto);
    CommentResponseDto updateComment(String userId, String commentId, CommentUpdateDto commentUpdateDto);
    CommonResponseDto deleteComment(String userId, String commentId);
    CommentResponseDto getCommentById(String commentId);
    PaginationResponseDto<CommentResponseDto> getAllCommentByPostId(
            PaginationFullRequestDto paginationFullRequestDto, String postId);
    PaginationResponseDto<CommentResponseDto> getAllReplyByParentCommentId(
            PaginationFullRequestDto paginationFullRequestDto, String parentCommentId);
}
