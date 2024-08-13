package com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.services;


import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.dto.PostDTO;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.entities.PostEntity;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.example.coddingshuttle.anuj.prod_ready_features.prod_ready_features.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
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

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {

        PostEntity olderPostEntity=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id "+postId));
        //update id of inputPost if  it is null (Title and Description is sent from front end!)
        inputPost.setId(postId);

        //use the model mapper to update the retrieved olderPostEntity
        modelMapper.map(inputPost,olderPostEntity);
        PostEntity savedPostEntity =postRepository.save(olderPostEntity);

        return modelMapper.map(savedPostEntity,PostDTO.class);

    }
}
