package com.example.postservice.controller;

import com.example.postservice.model.Comment;
import com.example.postservice.model.Like;
import com.example.postservice.model.Post;
import com.example.postservice.payload.PostRequest;
import com.example.postservice.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.createPost(postRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PostMapping("/{postId}/comment")
    public void addComment(@PathVariable String postId, @RequestBody Comment comment) {
        postService.addComment(postId, comment);
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable String postId, @RequestBody Like like) {
        postService.likePost(postId, like);
    }
}
