package com.wwanat.CryptoWorld;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CryptoWorldApplication {

    public static void main(String[] args) {
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase("local");

            database.drop();
            System.out.println("Collections dropped - database is clear");

            // create a collection
            database.createCollection("crypto");
            database.createCollection("crypto_details");
            database.createCollection("cryptocurrency_historical_values");
            database.createCollection("dbversion");
            database.createCollection("notifications");
            database.createCollection("users");
            System.out.println("Collections created successfully");
        }
        SpringApplication.run(CryptoWorldApplication.class, args);
    }

}
