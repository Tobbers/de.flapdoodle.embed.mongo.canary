package example;

import com.mongodb.*;
import de.flapdoodle.embed.mongo.commands.MongodArguments;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;


public class MongoConfigurationA {
    @Bean
    MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        var transactionManager = new MongoTransactionManager(dbFactory);
        transactionManager.setOptions(TransactionOptions.builder()
                .readConcern(ReadConcern.MAJORITY)
                .writeConcern(WriteConcern.MAJORITY)
                .build());

        return transactionManager;
    }


    @Bean
    public MongodArguments mongodArguments(){
        return MongodArguments.builder()
                .replication(Storage.of("rs0", 5000))
                .build();
    }

    @Bean
    public MongoClientSettings mongoClient(Net net) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:" + net.getPort()))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }
}
