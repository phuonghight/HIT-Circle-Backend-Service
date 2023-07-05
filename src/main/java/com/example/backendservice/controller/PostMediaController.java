package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.service.PostMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestApiV1
public class PostMediaController {

    private final PostMediaService postMediaService;
    @Tag(name = "postmedia-controller")
    @Operation(summary = "API get postmedia")
    @GetMapping( value = UrlConstant.PostMedia.GET_POSTMEDIA)
    public ResponseEntity<?> getPostMediaById(@PathVariable String postMediaId) {
        return VsResponseUtil.success(postMediaService.getPostMediaById(postMediaId));
    }
}
