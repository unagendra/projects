package com.coddingshuttle.SecurityApp.SecuityApplication.utils;

import com.coddingshuttle.SecurityApp.SecuityApplication.dto.PostDTO;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.PostEntity;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostSecurity {
    private  final PostService postService;

    //Get the Post by ID only if you are the owner of the Post...

    public boolean isOwnerOfPost(Long postId){
        //Get the Current User from Security Context
       User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       //Get the Post Entity from the Post ID
        PostDTO post=postService.getPostById(postId);

        //If the User ID of the Post matches with the current User Id (only allow to access the end point if there is a match)
        return  post.getAuthor().getId().equals(user.getId());

    }

}
