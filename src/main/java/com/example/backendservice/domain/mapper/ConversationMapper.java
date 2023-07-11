package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.response.ConversationDto;
import com.example.backendservice.domain.entity.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    @Mappings({
            @Mapping(target = "firstUserId", source = "conversation.firstUser.id"),
            @Mapping(target = "secondUserId", source = "conversation.secondUser.id")
    })
    ConversationDto toConversationDto(Conversation conversation);

    @Mappings({
            @Mapping(target = "firstUserId", source = "conversation.firstUser.id"),
            @Mapping(target = "secondUserId", source = "conversation.secondUser.id")
    })
    List<ConversationDto> toConversationDto(List<Conversation> conversations);

}
