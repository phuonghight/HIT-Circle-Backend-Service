package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.response.PostMediaDto;
import com.example.backendservice.domain.entity.PostMedia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMediaMapper {
    @Mappings({
            @Mapping(target = "postId", source = "postMedia.post.id")
    })
    PostMediaDto postMediaToPostMediaDto(PostMedia postMedia);
}
