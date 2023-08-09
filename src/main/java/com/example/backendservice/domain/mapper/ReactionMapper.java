package com.example.backendservice.domain.mapper;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.domain.dto.request.ReactionRequestDto;
import com.example.backendservice.domain.dto.response.ReactionDto;
import com.example.backendservice.domain.dto.response.ReactionNotificationResponseDto;
import com.example.backendservice.domain.entity.Reaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReactionMapper {
    @Mappings({
            @Mapping(target = "name", source = "name")
    })
    Reaction reactionRequestDtoToReaction(ReactionRequestDto reactionRequestDto);
    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "avatar", source = "user.avatar", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(target = "postId", source = "post.id")
    })
    ReactionDto reactionToReactionDto(Reaction reaction);

    @Mappings({
            @Mapping(target = "notificationMessage", ignore = true),
            @Mapping(target = "username", source = "user.username"),
            @Mapping(target = "avatar", source = "user.avatar", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL),
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "createdDate", source = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "lastModifiedDate", source = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME)
    })
    ReactionNotificationResponseDto reactionToReactionNotificationResponseDto(Reaction reaction);

    //List<ReactionDto> reactionsToReactionDtos(List<Reaction> reactions);
    default List<ReactionDto> reactionsToReactionDtos(List<Reaction> reactions) {
        List<ReactionDto> reactionDtos = new ArrayList<>();
        for (Reaction reaction : reactions) {
            ReactionDto reactionDto = reactionToReactionDto(reaction);
            reactionDtos.add(reactionDto);
        }
        return reactionDtos;
    }
}
