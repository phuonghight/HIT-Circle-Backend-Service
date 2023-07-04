package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.FollowResponseDto;
import com.example.backendservice.domain.entity.Follow;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.FollowMapper;
import com.example.backendservice.exception.AlreadyExistException;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.FollowRepository;
import com.example.backendservice.service.FollowService;
import com.example.backendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    private final UserService userService;

    @Override
    public FollowResponseDto follow(String currentUserId, String toUserId) {
        User fromUser = userService.getUserById(currentUserId);
        User toUser = userService.getUserById(toUserId);

        // xử lý case nếu from đã follow to
        Optional<Follow> findFollowed = followRepository.findFollowByFromIdAndToId(currentUserId, toUserId);
        if (findFollowed.isPresent()) {
            throw new AlreadyExistException(ErrorMessage.Follow.ERR_ALREADY_EXIST,
                    new String[]{toUser.getFullName()});
        }

        Follow follow = new Follow(fromUser, toUser);
        followRepository.save(follow);

        return followMapper.toResponseDto(follow);
    }

    @Override
    public CommonResponseDto unfollow(String currentUserId, String toUserId) {
        User fromUser = userService.getUserById(currentUserId);
        User toUser = userService.getUserById(toUserId);

        // if FROM haven't following TO => throw exception
        Follow follow = followRepository.findFollowByFromIdAndToId(fromUser.getId(), toUser.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.Follow.ERR_NOT_FOUND,
                            new String[]{toUser.getFullName()});
                });

        followRepository.delete(follow);

        return new CommonResponseDto(true, MessageConstant.UNFOLLOW_SUCCESSFULLY + toUser.getFullName());
    }
}
