package com.coddingshuttle.SecurityApp.SecuityApplication.services;


import com.coddingshuttle.SecurityApp.SecuityApplication.dto.PostDTO;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.PostEntity;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.exceptions.ResourceNotFoundException;
import com.coddingshuttle.SecurityApp.SecuityApplication.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository
                .findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Whenever the New Post is created, You should assign Current User of that post
     * @param inputPost
     * @return
     */
    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        //TODO get the current user from Security Context Holder
       User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        //TODO You are assigning the current user while creating post as Author and save it to DB
        postEntity.setAuthor(user);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {

        //get the user from the SecurityContextHolder and log to the console
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        log.info("user {}", user);


        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }
}
