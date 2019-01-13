package org.orson.app.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.orson.app.pojo.Person;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private MongoClient mongoClient;

    public UserDaoImpl() {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(codecRegistry).build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public Boolean auth(String username, String password) {
        MongoDatabase members = mongoClient.getDatabase("members");

        MongoCollection<Person> persons = members.getCollection("persons", Person.class);

        Optional<Person> person = Optional.ofNullable(persons.find(Filters.and(Filters.eq("name", username), Filters.eq("password", password))).first());

        return person.isPresent();

    }

    @Override
    public Boolean register(String username, String password) {
        MongoDatabase members = mongoClient.getDatabase("members");

        MongoCollection<Person> persons = members.getCollection("persons", Person.class);

        try {
            persons.insertOne(new Person(username, password));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
