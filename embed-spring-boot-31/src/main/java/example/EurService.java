package example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class EurService {

    private final MyRepository myRepository;

    public EurService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    @Transactional
    public void upsertEur() {
        myRepository.save(new MyEntity("random String"));
        throw new IllegalArgumentException();
    }


    
}
