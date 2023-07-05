package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class FollowController {

    private final FollowService followService;

    @Tag(name = "follow-controller")
    @Operation(summary = "API follow another user")
    @PostMapping(UrlConstant.Follow.FOLLOW)
    public ResponseEntity<?> follow(@Valid @RequestBody FollowRequestDto request,
                                    @Parameter(name = "user", hidden = true)
                                        @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(followService.follow(user.getId(), request.getUserId()));
    }

    @Tag(name = "follow-controller")
    @Operation(summary = "API unfollow one user")
    @DeleteMapping(UrlConstant.Follow.UNFOLLOW)
    public ResponseEntity<?> unfollow(@Valid @RequestBody FollowRequestDto request,
                                    @Parameter(name = "user", hidden = true)
                                    @CurrentUser UserPrincipal user) {
        return VsResponseUtil.success(followService.unfollow(user.getId(), request.getUserId()));
    }

}
