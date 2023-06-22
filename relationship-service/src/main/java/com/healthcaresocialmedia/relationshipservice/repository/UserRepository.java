package com.healthcaresocialmedia.relationshipservice.repository;

import com.healthcaresocialmedia.relationshipservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends Neo4jRepository<User, String> {
    @Query(value = "MATCH (user:User{id: $userId})<-[r:IS_FOLLOWING]-(follower:User) RETURN follower")
    List<User> findFollowers(@Param("userId") String userId);

    @Query(value = "MATCH (user:User{id: $userId})-[r:IS_FOLLOWING]->(followed:User) RETURN followed")
    List<User> findFollowing(@Param("userId") String userId);
}
