package com.example.backendservice.controller;

import com.example.backendservice.base.RestApiV1;
import com.example.backendservice.base.VsResponseUtil;
import com.example.backendservice.constant.UrlConstant;
import com.example.backendservice.domain.dto.pagination.PaginationFullRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationRequestDto;
import com.example.backendservice.domain.dto.request.ChangePasswordRequestDto;
import com.example.backendservice.domain.dto.pagination.PaginationSortRequestDto;
import com.example.backendservice.domain.dto.request.FollowRequestDto;
import com.example.backendservice.domain.dto.request.UserUpdateDto;
import com.example.backendservice.security.CurrentUser;
import com.example.backendservice.security.UserPrincipal;
import com.example.backendservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestApiV1
public class UserController {

  private final UserService userService;

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API get user")
  @GetMapping(UrlConstant.User.GET_USER)
  public ResponseEntity<?> getUserById(@PathVariable String userId) {
    return VsResponseUtil.success(userService.getUserById(userId));
  }

  @Tags({@Tag(name = "user-controller-admin"), @Tag(name = "user-controller")})
  @Operation(summary = "API get current user login")
  @GetMapping(UrlConstant.User.GET_CURRENT_USER)
  public ResponseEntity<?> getCurrentUser(@Parameter(name = "principal", hidden = true)
                                          @CurrentUser UserPrincipal principal) {
    return VsResponseUtil.success(userService.getCurrentUser(principal));
  }

  @Tag(name = "user-controller-admin")
  @Operation(summary = "API get all customer")
  @GetMapping(UrlConstant.User.GET_USERS)
  public ResponseEntity<?> getCustomers(@Valid @ParameterObject PaginationFullRequestDto requestDTO) {
    return VsResponseUtil.success(userService.getCustomers(requestDTO));
  }

  // Update user's profile (user can update your profile)
  @Tag(name = "user-controller")
  @Operation(summary = "API update user's profile (user can update your profile)")
  @PatchMapping(value = UrlConstant.User.UPDATE_USER, consumes = "multipart/form-data")
  public ResponseEntity<?> updateProfile(@Valid @ModelAttribute UserUpdateDto userUpdateDto,
                                         @Parameter(name = "user", hidden = true)
                                         @CurrentUser UserPrincipal user) {
    return VsResponseUtil.success(userService.updateProfile(user.getId(), userUpdateDto));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API change user's password")
  @PostMapping(value = UrlConstant.User.CHANGE_PASSWORD)
  public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequestDto passwordRequestDto,
                                          @Parameter(name = "user", hidden = true)
                                         @CurrentUser UserPrincipal user) {
    return VsResponseUtil.success(userService.changePassword(user.getId(), passwordRequestDto));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API get users have been following target user")
  @GetMapping(value = UrlConstant.User.GET_FOLLOWERS_BY_USER_ID)
  public ResponseEntity<?> getFollowers(@Parameter(name = "user", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @Valid @ParameterObject PaginationRequestDto paginationRequestDto,
                                        @PathVariable String userId) {
    return VsResponseUtil.success(userService.getFollowers(paginationRequestDto, userId));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API get users that target user have been following")
  @GetMapping(value = UrlConstant.User.GET_FOLLOWING_BY_USER_ID)
  public ResponseEntity<?> getFollowing(@Parameter(name = "user", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @Valid @ParameterObject PaginationRequestDto paginationRequestDto,
                                        @PathVariable String userId) {
    return VsResponseUtil.success(userService.getFollowing(paginationRequestDto, userId));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API get my friends")
  @GetMapping(value = UrlConstant.User.GET_FRIENDS)
  public ResponseEntity<?> getMyFriends(@Parameter(name = "user", hidden = true)
                                        @CurrentUser UserPrincipal user,
                                        @Valid @ParameterObject PaginationFullRequestDto paginationFullRequestDto) {
    return VsResponseUtil.success(userService.getFriendsById(paginationFullRequestDto, user.getId()));
  }

  @Tag(name = "user-controller")
  @Operation(summary = "API search user by username or full name")
  @GetMapping(value = UrlConstant.User.SEARCH_ANOTHER_USER)
  public ResponseEntity<?>
  searchUserByUsername(@Parameter(name = "user", hidden = true)
                       @CurrentUser UserPrincipal user,
                       @Valid @ParameterObject PaginationRequestDto paginationRequestDto,
                       @RequestParam String q
  ) {
    return VsResponseUtil.success(userService
            .searchUserByUsername(paginationRequestDto, q, user.getId()));
  }

}
