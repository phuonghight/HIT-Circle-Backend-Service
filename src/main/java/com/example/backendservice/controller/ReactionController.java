package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.request.ReactionRequestDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class ReactionController {

    private final ReactionService reactionService;
    @Tag(name = "reaction-controller")
    @Operation(summary = "API react post and change react")
    @PostMapping( value = UrlConstant.Reaction.CREATE_UPDATE_REACTION)
    public ResponseEntity<?> reactPost(@Valid @RequestBody ReactionRequestDto reactionRequestDto,
                                           @Parameter(name = "user", hidden = true)
                                           @CurrentUser UserPrincipal user    ) {
        return VsResponseUtil.success(reactionService.react(user.getId(), reactionRequestDto));
    }

    @Tag(name = "reaction-controller")
    @Operation(summary = "API remove reaction")
    @DeleteMapping( value = UrlConstant.Reaction.REMOVE_REACTION)
    public ResponseEntity<?> removeReact(@PathVariable String postId,
                                            @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user    ) {
        return VsResponseUtil.success(reactionService.removeReact(user.getId(), postId));
    }

    @Tag(name = "reaction-controller")
    @Operation(summary = "API get my reaction")
    @GetMapping( value = UrlConstant.Reaction.GET_MY_REACTION)
    public ResponseEntity<?> getMyReactionByPostId(@PathVariable String postId,
                                            @Parameter(name = "user", hidden = true)
                                            @CurrentUser UserPrincipal user    ) {
        return VsResponseUtil.success(reactionService.findReactionByUserIdAndPostId(user.getId(), postId));
    }

    @Tag(name = "reaction-controller")
    @Operation(summary = "API get user reaction")
    @GetMapping( value = UrlConstant.Reaction.GET_USER_REACTION)
    public ResponseEntity<?> getReactionByUserIdAndPostId(@RequestParam String userId,
                                                          @PathVariable String postId) {
        return VsResponseUtil.success(reactionService.findReactionByUserIdAndPostId(userId, postId));
    }

    @Tag(name = "reaction-controller")
    @Operation(summary = "API get all reaction")
    @GetMapping( value = UrlConstant.Reaction.GET_ALL_REACTION)
    public ResponseEntity<?> getAllReactionByPostId(@PathVariable String postId) {
        return VsResponseUtil.success(reactionService.findAllReactionByPostId(postId));
    }

    @Tag(name = "reaction-controller")
    @Operation(summary = "API get all reaction by type")
    @GetMapping( value = UrlConstant.Reaction.GET_ALL_BY_TYPE_REACTION)
    public ResponseEntity<?> getAllReactionByReactionName(@Valid @RequestBody ReactionRequestDto reactionRequestDto) {
        return VsResponseUtil.success(reactionService.findAllReactionByReactionName(reactionRequestDto));
    }
}
