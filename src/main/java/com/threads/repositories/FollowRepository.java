package com.threads.repositories;

import com.threads.entities.FollowEntity;
import com.threads.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    Boolean existsByFollowerAndFollowing(UserEntity follower, UserEntity following);

    Long countByFollowing(UserEntity user); // followers count

    Long countByFollower(UserEntity user); // following count

    List<FollowEntity> findByFollowing(UserEntity user);
    List<FollowEntity> findByFollower(UserEntity user);
}
