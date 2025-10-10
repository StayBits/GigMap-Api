package com.staybits.gigmapapi.communities.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.staybits.gigmapapi.communities.domain.model.aggregates.Community;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    boolean existsByName(String name);

    //@Query("SELECT c.members FROM Community c WHERE c.id = :id")
    //List<User> findMembersByCommunityId(@Param("id") Long communityId);
}
