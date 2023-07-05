package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.response.FollowResponseDto;
import com.example.backendservice.domain.entity.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FollowMapper {

    FollowResponseDto toResponseDto(Follow follow);

}
