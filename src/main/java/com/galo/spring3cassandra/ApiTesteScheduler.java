package com.galo.spring3cassandra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiTesteScheduler {

  static int count;

  private static final Logger logger = LoggerFactory.getLogger(ApiTesteScheduler.class);
  @Value("${apps.api-teste.url}")
  public String URL;

  private final RestTemplate restTemplate = new RestTemplate();

  @Scheduled(fixedRate = 10000) // a cada 10 segundos
  public void getAllUser() {
    try {
      String response = restTemplate.getForObject(URL + "/users", String.class);
      logger.info("api teste get: {}", response);
    } catch (Exception e) {
      logger.error("Erro ao chamar /all users: {}", e.getMessage());
    }
  }

  @Scheduled(fixedRate = 15000) // a cada 15 segundos
  public void checkOtherAppHealth() {
    try {
      String response = restTemplate.postForObject(URL + "/users", "Nome", String.class);

      logger.info("create user: {}", response);

      restTemplate.delete(URL + "/users/" + response.split(":")[1]);

      logger.info("Deleted user {}", response);
    } catch (Exception e) {
      logger.error("Error ao create/delete user : {}", e.getMessage());
    }
  }

  @Scheduled(fixedRate = 3600000) // limpar a cada 1h
  public void cleanDatabaseFromFourthRecord() {
    try {
      // Busca todos os livros
      Book[] books = restTemplate.getForObject(URL, Book[].class);

      if (books != null && books.length > 3) {
        for (int i = 3; i < books.length; i++) {
          restTemplate.delete(URL + "/" + books[i].getId());
          logger.info("Deleted book {}", books[i]);
        }
      } else {
        logger.info("Menos de 4 registros, nada para limpar.");
      }
    } catch (Exception e) {
      logger.error("Erro ao limpar base: {}", e.getMessage());
    }
  }
}
