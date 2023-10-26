package example;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class MyEntity {

    @Id
    private String mongoId;

    private String randomField;

    public MyEntity(String randomField){
        this.randomField = randomField;
    }
}
