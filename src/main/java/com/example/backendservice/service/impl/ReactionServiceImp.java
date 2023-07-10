package com.example.backendservice.service.impl;

import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.constant.MessageConstant;
import com.example.backendservice.domain.dto.request.ReactionRequestDto;
import com.example.backendservice.domain.dto.response.CommonResponseDto;
import com.example.backendservice.domain.dto.response.ReactionDto;
import com.example.backendservice.domain.entity.Post;
import com.example.backendservice.domain.entity.Reaction;
import com.example.backendservice.domain.entity.User;
import com.example.backendservice.domain.mapper.ReactionMapper;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.PostRepository;
import com.example.backendservice.repository.ReactionRepository;
import com.example.backendservice.repository.UserRepository;
import com.example.backendservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionServiceImp implements ReactionService {
    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReactionMapper reactionMapper;

    @Override
    public ReactionDto react(String userId, ReactionRequestDto reactionRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        Post post = postRepository.findById(reactionRequestDto.getPostId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{reactionRequestDto.getPostId()}));
        Reaction reaction = reactionRepository.findByUserIdAndPostId(userId, reactionRequestDto.getPostId());
        if (reaction == null) {
            reaction = reactionMapper.reactionRequestDtoToReaction(reactionRequestDto);
            reaction.setUser(user);
            reaction.setPost(post);
        }
        else {
            reaction.setName(reactionRequestDto.getName());
        }
        return reactionMapper.reactionToReactionDto(reactionRepository.save(reaction));
    }

    @Override
    public CommonResponseDto removeReact(String userId, String postId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        Reaction reaction = reactionRepository.findByUserIdAndPostId(userId, postId);
        if (reaction == null) {
            throw new NotFoundException(ErrorMessage.Reaction.ERR_NOT_FOUND, new String[]{postId} );
        }
        reactionRepository.delete(reaction);
        return new CommonResponseDto(true, MessageConstant.REMOVE_REACTION_SUCCESSFUL + reaction.getName());
    }

    @Override
    public ReactionDto findReactionByUserIdAndPostId(String userId, String postId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        Reaction reaction = reactionRepository.findByUserIdAndPostId(userId, postId);
        return reactionMapper.reactionToReactionDto(reaction);
    }

    @Override
    public List<ReactionDto> findAllReactionByPostId(String postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        List<Reaction> reactions = reactionRepository.findAllByPostId(postId);
        return reactionMapper.reactionsToReactionDtos(reactions);
    }
}
