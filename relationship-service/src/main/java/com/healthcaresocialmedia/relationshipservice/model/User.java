package com.healthcaresocialmedia.relationshipservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@Data
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    @JsonManagedReference
    @Relationship(value = "IS_FOLLOWING", direction = Relationship.Direction.OUTGOING)
    private Set<Friendship> friendships;
}
