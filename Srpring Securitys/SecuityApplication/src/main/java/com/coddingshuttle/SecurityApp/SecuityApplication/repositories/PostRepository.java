package com.coddingshuttle.SecurityApp.SecuityApplication.repositories;


import com.coddingshuttle.SecurityApp.SecuityApplication.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
