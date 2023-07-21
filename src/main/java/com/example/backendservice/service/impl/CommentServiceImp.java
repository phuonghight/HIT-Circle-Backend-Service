package com.example.backendservice.service.impl;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.constant.SortByDataConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationResponseDto;
import com.example.backendservice.domain.dto.pagination.PagingMeta;
import com.example.backendservice.domain.dto.request.CommentCreateDto;
import com.example.backendservice.domain.dto.request.CommentUpdateDto;
import com.example.backendservice.domain.dto.response.CommentResponseDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.entity.Comment;
import com.example.backendservice.domain.entity.Post;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.CommentMapper;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.exception.UnauthorizedException;
import com.example.backendservice.repository.CommentRepository;
import com.example.backendservice.repository.PostRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.service.CommentService;
import com.example.backendservice.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private  final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Override
    public CommentResponseDto sendComment(String userId, CommentCreateDto commentCreateDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        Post post = postRepository.findById(commentCreateDto.getPostId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{commentCreateDto.getPostId()}));

        Comment comment = commentMapper.commentCreateDtoToComment(commentCreateDto);
        comment.setUser(user);
        comment.setPost(post);
        if (commentCreateDto.getParentCommentId() != null && !commentCreateDto.getParentCommentId().isEmpty()) {
            Comment parentComment = commentRepository.findById(commentCreateDto.getParentCommentId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_ID, new String[]{commentCreateDto.getParentCommentId()}));

            if (parentComment.getLevel() + 1 > CommonConstant.MAX_OF_LEVEL_COMMENT) {
                Comment parentCommentLast = commentRepository.findById(parentComment.getParentComment().getId())
                        .orElseThrow(() -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_ID, new String[]{parentComment.getParentComment().getId()}));
                comment.setParentComment(parentCommentLast);
                comment.setLevel(CommonConstant.MAX_OF_LEVEL_COMMENT);
            }
            else {
                comment.setParentComment(parentComment);
                comment.setLevel(parentComment.getLevel() + 1);
            }
        }
        else {
            comment.setParentComment(null);
            comment.setLevel(1);
        }
        return commentMapper.commentToCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto updateComment(String userId, String commentId, CommentUpdateDto commentUpdateDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_ID, new String[]{commentId}));
        if (userId.equals(comment.getUser().getId())) {
            commentMapper.updateComment(comment, commentUpdateDto);
        }
        else {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
        return commentMapper.commentToCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommonResponseDto deleteComment(String userId, String commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_ID, new String[]{commentId}));
        if (userId.equals(comment.getUser().getId())) {
            commentRepository.delete(comment);
            return new CommonResponseDto(true, MessageConstant.DELETE_COMMENT_SUCCESSFULLY);
        }
        else {
            throw new UnauthorizedException(ErrorMessage.FORBIDDEN_UPDATE_DELETE);
        }
    }

    @Override
    public PaginationResponseDto<CommentResponseDto> getAllCommentByPostId(PaginationFullRequestDto paginationFullRequestDto, String postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));

        paginationFullRequestDto.setIsAscending(true);
        paginationFullRequestDto.setPageSize(CommonConstant.NUM_OF_COMMENT_PER_PAGE_DEFAULT);

        Pageable pageable = PaginationUtil
                .buildPageable(paginationFullRequestDto, SortByDataConstant.Comment);
        Page<Comment> commentPage = commentRepository.getAllCommentByPostId(postId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.Comment, commentPage);

        List<CommentResponseDto> commentResponseDtoList =
                commentMapper.commentListToCommentResponseDtoList(commentPage.getContent());
        return new PaginationResponseDto<>(meta, commentResponseDtoList);
    }

    @Override
    public PaginationResponseDto<CommentResponseDto> getAllReplyByParentCommentId(PaginationFullRequestDto paginationFullRequestDto, String parentCommentId) {
        commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Comment.ERR_NOT_FOUND_ID, new String[]{parentCommentId}));

        paginationFullRequestDto.setIsAscending(true);
        paginationFullRequestDto.setPageSize(CommonConstant.NUM_OF_COMMENT_PER_PAGE_DEFAULT);

        Pageable pageable = PaginationUtil
                .buildPageable(paginationFullRequestDto, SortByDataConstant.Comment);
        Page<Comment> commentPage = commentRepository.getAllReplyByParentCommentId(parentCommentId, pageable);
        PagingMeta meta = PaginationUtil
                .buildPagingMeta(paginationFullRequestDto, SortByDataConstant.Comment, commentPage);

        List<CommentResponseDto> commentResponseDtoList =
                commentMapper.commentListToCommentResponseDtoList(commentPage.getContent());
        return new PaginationResponseDto<>(meta, commentResponseDtoList);
    }
}
