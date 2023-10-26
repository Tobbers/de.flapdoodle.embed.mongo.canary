package example;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyRepository {

    private final MongoTemplate mongoTemplate;

    public MyRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void save(MyEntity entity){
        mongoTemplate.save(entity);
    }

}
