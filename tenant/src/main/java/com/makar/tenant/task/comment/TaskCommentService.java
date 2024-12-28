package com.makar.tenant.task.comment;

import com.makar.tenant.task.comment.rest.model.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;

    private final TaskCommentMapper taskCommentMapper;

    public void create(CommentRequest request) {
        var comment = taskCommentMapper.toEntity(request);
        taskCommentRepository.save(comment);
    }
}
