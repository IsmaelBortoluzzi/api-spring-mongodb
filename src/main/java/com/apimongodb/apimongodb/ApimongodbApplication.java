package com.apimongodb.apimongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class ApimongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApimongodbApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate) {
        return args -> {
            Address address = new Address("USA", "Bismarck", "PQRS");
            Student student = new Student(
                    "Maria",
                    "Luiza",
                    "marialuiza@gmail.com",
                    Gender.FEMALE,
                    address,
                    List.of("Computer Science", "German"),
                    BigDecimal.TEN,
                    LocalDateTime.now()
            );

            String email = "marialuiza@gmail.com";
//            Query query = new Query();
//            query.addCriteria(Criteria.where("email").is(email));
//
//            List<Student> students = mongoTemplate.find(query, Student.class);
//
//            if (students.isEmpty()) {
//                repository.insert(student);
//            }
            repository.findStudentByEmail(email)
                      .ifPresentOrElse(
                          row -> {
                              System.out.println("Student Already Exists");
                          },
                          () -> {
                              repository.insert(student);
                          }
                      );
        };
    }

}
