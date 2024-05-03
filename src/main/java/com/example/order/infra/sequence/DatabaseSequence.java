package com.example.order.infra.sequence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "sequences_collection")
public class DatabaseSequence {

    @Id
    private String id;
    private long seq;

}