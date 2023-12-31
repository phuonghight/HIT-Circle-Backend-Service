package com.example.backendservice.socket.io.event;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.example.backendservice.constant.CommonConstant;
import com.example.backendservice.constant.ErrorMessage;
import com.example.backendservice.domain.dto.request.CommentCreateDto;
import com.example.backendservice.domain.dto.response.CommentNotificationResponseDto;
import com.example.backendservice.domain.dto.response.CommentResponseDto;
import com.example.backendservice.domain.entity.Comment;
import com.example.backendservice.domain.mapper.CommentMapper;
import com.example.backendservice.exception.NotFoundException;
import com.example.backendservice.repository.CommentRepository;
import com.example.backendservice.repository.PostRepository;
import com.example.backendservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommentEventHandler {
    private final SocketIOServer server;
    private final CommentService commentService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @OnEvent(CommonConstant.Event.CLIENT_JOIN_ROOM)
    public void handlerEventJoinRoom(SocketIOClient client, String postId){
        postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Post.ERR_NOT_FOUND_ID, new String[]{postId}));
        log.info("User {} join room: {}", client.get(CommonConstant.Key.USER_ID), postId);
        client.joinRoom(postId);
    }

    @OnEvent(CommonConstant.Event.CLIENT_SEND_COMMENT)
    public void handlerEventSendComment(SocketIOClient client, CommentCreateDto commentData){
        log.info("User {} send comment: {}", client.get(CommonConstant.Key.USER_ID), commentData.getComment());

        Comment comment = commentService
                .sendComment(client.get(CommonConstant.Key.USER_ID), commentData);
        CommentResponseDto commentResponseDto = commentMapper.commentToCommentResponseDto(comment);
        CommentNotificationResponseDto commentNotificationResponseDto =
                commentMapper.commentToCommentNotificationResponseDto(comment);
        commentNotificationResponseDto.setNotificationMessage(
                comment.getUser().getUsername() + " đã thêm 1 bình luận mới");

        //Server gửi comment cho post có phòng là postId
        server.getRoomOperations(commentResponseDto.getPostId())
                .sendEvent(CommonConstant.Event.SERVER_SEND_COMMENT, commentResponseDto);

        //Server gửi thông báo về phòng (userId) liên quan
        List<String> listUserId = new ArrayList<>();
        if (commentResponseDto.getParentCommentId() != null) {
            listUserId = commentRepository
                    .findAllUserIdNotification(commentResponseDto.getParentCommentId(), commentResponseDto.getPostId(), commentResponseDto.getUserId());
        }
        else {
            listUserId = commentRepository
                    .findAllUserIdNotification(commentResponseDto.getPostId(), commentResponseDto.getUserId());
        }
        if (!listUserId.isEmpty()) {
            for (String userId : listUserId) {
                server.getRoomOperations(userId)
                        .sendEvent(CommonConstant.Event.SERVER_SEND_COMMENT_NOTIFICATION, commentNotificationResponseDto);
            }
        }

    }

}
