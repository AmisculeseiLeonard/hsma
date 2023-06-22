package com.example.postservice.service;

import com.example.postservice.exception.MessagingException;
import com.example.postservice.exception.ResourceNotFoundException;
import com.example.postservice.messaging.PostEventSender;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Like;
import com.example.postservice.model.Post;
import com.example.postservice.payload.PostRequest;
import com.example.postservice.repository.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final PostEventSender postEventSender;

    public PostService(PostRepository postRepository, PostEventSender postEventSender) {
        this.postRepository = postRepository;
        this.postEventSender = postEventSender;
    }

    @Transactional
    public Post createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .userId(postRequest.getUserId())
                .description(postRequest.getDescription())
                .imageUrl(postRequest.getImageUrl())
                .createdAt(Instant.now())
                .build();

        Post savedPost = postRepository.save(post);
        try {
            postEventSender.sendPostEvent(savedPost);
        } catch (JsonProcessingException e) {
            log.error("Error occurred while trying to send a message", e);
            throw new MessagingException("Error occurred while trying to send a message");
        }
        return savedPost;
    }

    public List<Post> getPostsByIds(List<String> postsIds) {
        return postRepository.findAllById(postsIds);
    }

    public void addComment(String postId, Comment comment) {
        Post post = getPost(postId);
        if(post.getComments() == null) {
            post.setComments(new HashSet<>());
        }
        post.getComments().add(comment);
        postRepository.save(post);
    }

    public void likePost(String postId, Like like) {
        Post post = getPost(postId);
        if(post.getLikes() == null) {
            post.setLikes(new HashSet<>());
        }
        post.getLikes().add(like);
        postRepository.save(post);
    }

    public Post getPost(String postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Invalid post ID"));
    }


}
