package com.example.order.application.service;

import com.example.order.adapter.persistence.entity.DatabaseSequence;
import com.example.order.port.out.SequenceGeneratorOutputPort;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@AllArgsConstructor
@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorOutputPort {

    private final MongoOperations mongoOperations;

    @Override
    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq", 1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);

        return Objects.isNull(counter) ? 1 : counter.getSeq();
    }

}
