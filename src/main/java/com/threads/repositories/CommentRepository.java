package com.threads.repositories;

import com.threads.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query(value = "SELECT * FROM comments c WHERE c.user_id = :userId", nativeQuery = true)
    List<CommentEntity> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM comments c WHERE c.post_id = :postId", nativeQuery = true)
    List<CommentEntity> findAllCommentsOnPost(@Param("postId") Long postId);
}