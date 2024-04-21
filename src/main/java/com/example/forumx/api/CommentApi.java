package com.example.forumx.api;

import com.example.forumx.model.CommentModel;
import com.example.forumx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentApi {
    private final CommentService commentService;

    @Autowired
    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments/{threadId}")
    public ResponseEntity<Page<CommentModel>> getComments(@PathVariable("threadId") Long threadId, @RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(commentService.getCommentByThread(threadId, page, size));
    }

    @PostMapping("/comments")
    public void createComment(@RequestBody CommentModel commentModel) {
        commentService.createComment(commentModel);
    }

    //chi cho sua noi dung, thu dung api sua noi dung xem co map duoc khong
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@RequestBody CommentModel commentModel, @PathVariable("commentId") Long commentId) {
       return ResponseEntity.ok(commentService.updateComment(commentModel, commentId));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }





}
