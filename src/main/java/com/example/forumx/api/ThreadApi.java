package com.example.forumx.api;

import com.example.forumx.model.ThreadModel;
import com.example.forumx.service.ThreadService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ThreadApi {
    private final ThreadService threadService;

    public ThreadApi(ThreadService threadService) {
        this.threadService = threadService;
    }

    @GetMapping("/threads")
    public ResponseEntity<Page<ThreadModel>> getThreads(@RequestParam(required = false, defaultValue = "0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size){
        return ResponseEntity.ok(threadService.getAllThreads(page, size));
    }

    @GetMapping("/threads/{threadId}")
    public ResponseEntity<ThreadModel> getThread(@PathVariable("threadId") Long threadId) {
        return ResponseEntity.ok(threadService.getThread(threadId));
    }

    @GetMapping("/threads/category/{categoryId}")
    public ResponseEntity<Page<ThreadModel>> getThreadsByCategory(@PathVariable("categoryId") Long categoryId,
                                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(threadService.getThreadsByCategory(categoryId, page, size));
    }

    @GetMapping("/threads/user/{userId}")
    public ResponseEntity<Page<ThreadModel>> getThreadsByUser(@PathVariable("userId") Long userId,
                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(threadService.getThreadsByUser(userId, page, size));
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/threads")
    public void createThread(@RequestBody ThreadModel threadModel) {
        threadService.createThread(threadModel);
    }

    @PutMapping("/threads/{threadId}")
    public ResponseEntity<ThreadModel> updateThread(@RequestBody ThreadModel threadModel, @PathVariable("threadId") Long threadId){
        return ResponseEntity.ok(threadService.updateThread(threadModel, threadId));
    }

    @DeleteMapping("/threads/{threadId}")
    public void deleteThread(@PathVariable("threadId") Long threadId) {
        threadService.deleteThread(threadId);
    }


}
