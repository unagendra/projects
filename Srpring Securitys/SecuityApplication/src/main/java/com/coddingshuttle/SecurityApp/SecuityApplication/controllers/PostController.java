package com.coddingshuttle.SecurityApp.SecuityApplication.controllers;

import com.coddingshuttle.SecurityApp.SecuityApplication.dto.PostDTO;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.PostService;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})   //only USER and ADMIN can access this method
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }


    @GetMapping("/{postId}")
    //@PreAuthorize("hasAnyRole('USER','ADMIN') OR hasAuthority('POST_VIEW')")
    @PreAuthorize("@postSecurity.isOwnerOfPost(#postId)")     //Access the post only If you are the author of the post
    public PostDTO getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }

}
