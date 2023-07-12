package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {

    @Mappings({
            @Mapping(target = "conversation", ignore = true)
    })
    Message toMessage(MessageRequestDto messageRequestDto);

    MessageResponseDto toResponseDto(Message message);

    List<MessageResponseDto> toResponseDto(List<Message> messages);

}
