package com.coddingshuttle.SecurityApp.SecuityApplication.dto;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String description;
    private User author;
}
