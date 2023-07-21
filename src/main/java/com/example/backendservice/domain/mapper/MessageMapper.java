package com.example.backendservice.domain.mapper;

import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.domain.dto.request.MessageRequestDto;
import com.example.backendservice.domain.dto.response.MessageResponseDto;
import com.example.backendservice.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MessageMapper {

    Message toMessage(MessageRequestDto messageRequestDto);

    @Mappings({
            @Mapping(target = "senderId", source = "message.sender.id"),
            @Mapping(target = "receiverId", source = "message.receiver.id"),
            @Mapping(target = "createdDate", dateFormat = CommonConstant.PATTERN_DATE_TIME),
            @Mapping(target = "lastModifiedDate", dateFormat = CommonConstant.PATTERN_DATE_TIME)
    })
    MessageResponseDto toResponseDto(Message message);

    @Mappings({
            @Mapping(target = "senderId", source = "message.sender.id"),
            @Mapping(target = "receiverId", source = "message.receiver.id")
    })
    List<MessageResponseDto> toResponseDto(List<Message> messages);

}
