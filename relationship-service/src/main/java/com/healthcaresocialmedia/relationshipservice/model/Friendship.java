package com.healthcaresocialmedia.relationshipservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.time.Instant;
@Data
@RelationshipProperties
public class Friendship {
    @RelationshipId
    private Long id;

    private Instant friendshipStartDate;

    @TargetNode
    @JsonBackReference
    private User userFollowed;

    public Friendship() {
        friendshipStartDate = Instant.now();
    }

}
