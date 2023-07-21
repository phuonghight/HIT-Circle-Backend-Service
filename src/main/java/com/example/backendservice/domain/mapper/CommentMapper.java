package com.example.backendservice.domain.mapper;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.domain.dto.request.CommentCreateDto;
import com.example.backendservice.domain.dto.request.CommentUpdateDto;
import com.example.backendservice.domain.dto.response.CommentResponseDto;
import com.example.backendservice.domain.entity.Comment;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mappings({
            @Mapping(target = "comment", source = "comment")
    })
    Comment commentCreateDtoToComment(CommentCreateDto commentRequestDto);
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "parentCommentId", source = "parentComment.id"),
            @Mapping(target = "createdDate", source = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME)
    })
    CommentResponseDto commentToCommentResponseDto(Comment comment);
    @Mappings({
            @Mapping(target = "comment", source = "comment")
    })
    void updateComment(@MappingTarget Comment comment, CommentUpdateDto commentUpdateDto);

    //List<CommentResponseDto> commentListToCommentResponseDtoList(List<Comment> commentList);
    default List<CommentResponseDto> commentListToCommentResponseDtoList(List<Comment> commentList) {
        return commentList.stream()
                .map(this::commentToCommentResponseDto)
                .collect(Collectors.toList());
    }
}
