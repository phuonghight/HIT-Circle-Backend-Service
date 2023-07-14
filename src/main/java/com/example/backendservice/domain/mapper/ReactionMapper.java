package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.request.ReactionRequestDto;
import com.example.backendservice.domain.dto.response.ReactionDto;
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
            @Mapping(target = "postId", source = "post.id")
    })
    ReactionDto reactionToReactionDto(Reaction reaction);

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
