package com.example.backendservice.domain.mapper;

import com.example.backendservice.domain.dto.request.PostCreateDto;
import com.example.backendservice.domain.dto.request.PostUpdateDto;
import com.example.backendservice.domain.dto.response.PostDto;
import com.example.backendservice.domain.entity.Post;
import com.example.backendservice.domain.entity.PostMedia;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {
    @Mappings({
            @Mapping(target = "caption", source = "caption", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    })
    Post postCreateDtoToPost(PostCreateDto postCreateDto);

    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
            @Mapping(target = "mediaFiles", expression = "java(mapMediaFiles(post.getPostMedia()))")
    })
    PostDto postToPostDto(Post post);
    default List<String> mapMediaFiles(List<PostMedia> postMediaList) {
        List<String> mediaFiles = new ArrayList<>();
        if (postMediaList != null) {
            for (PostMedia postMedia : postMediaList) {
                mediaFiles.add(postMedia.getMediaFile());
            }
        }
        return mediaFiles;
    }

    @Mappings({
            @Mapping(target = "caption", source = "caption", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    })
    void updatePost(@MappingTarget Post post, PostUpdateDto postUpdateDto);

    //List<PostDto> postsToPostDtos(List<Post> posts);
    default List<PostDto> postsToPostDtos(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts) {
            PostDto postDto = postToPostDto(post);
            postDtos.add(postDto);
        }
        return postDtos;
    }
}
