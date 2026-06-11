package com.galo.spring3cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Spring3CassandraApplication {

  public static void main(String[] args) {
    SpringApplication.run(Spring3CassandraApplication.class, args);
  }

}
