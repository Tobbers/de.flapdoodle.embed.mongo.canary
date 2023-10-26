package example;

import com.mongodb.*;
import de.flapdoodle.embed.mongo.commands.MongodArguments;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.types.DistributionBaseUrl;
import de.flapdoodle.embed.process.io.directories.PersistentDir;
import de.flapdoodle.reverse.Transition;
import de.flapdoodle.reverse.transitions.Start;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

import java.nio.file.Path;

@Configuration
public class MongoConfigurationC {
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
    Mongod customGod(Net net) {
        return new Mongod() {
            @Override
            public Transition<DistributionBaseUrl> distributionBaseUrl() {
                return Start.to(DistributionBaseUrl.class)
                        .initializedWith(DistributionBaseUrl.of("local-artifactory-url"));
            }


            @Override
            public Transition<PersistentDir> persistentBaseDir() {
                String tmpdir = System.getProperty("java.io.tmpdir");

                return Start.to(PersistentDir.class)
                        .initializedWith(PersistentDir.of(Path.of(tmpdir)));
            }

            @Override
            public Transition<Net> net() {
                return Start.to(Net.class)
                        .providedBy(() -> net);
            }

            @Override
            public Transition<MongodArguments> mongodArguments() {
                return Start.to(MongodArguments.class).initializedWith(MongodArguments.builder()
                        .replication(Storage.of("rs0", 5000))
                        .build());
            }
        };
    }


    @Bean
    public MongoClientSettings mongoClient(Net net) {
        return MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://localhost:" + net.getPort()))
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();
    }
}
