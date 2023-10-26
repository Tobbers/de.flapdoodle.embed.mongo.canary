package example;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ExampleIT {

    @Autowired
    EurService eurService;

    @Test
    void example(@Autowired final MongoTemplate mongoTemplate) {
        try {
            eurService.upsertEur();
        } catch (RuntimeException e){

        }
        assertThat(mongoTemplate.count(new Query(), MyEntity.class )).isEqualTo(0);
    }
}
