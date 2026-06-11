package com.galo.spring3cassandra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthCheckScheduler {

  static int count;

  private static final Logger logger = LoggerFactory.getLogger(HealthCheckScheduler.class);
  public static final String URL = "http://localhost:8080/books";
  private final RestTemplate restTemplate = new RestTemplate();

  @Scheduled(fixedRate = 10000) // a cada 10 segundos
  public void checkMainAppHealth() {
    try {
      String response = restTemplate.getForObject(URL, String.class);
      logger.info("find all books: {}", response);
    } catch (Exception e) {
      logger.error("Erro ao chamar /books: {}", e.getMessage());
    }
  }

  @Scheduled(fixedRate = 15000) // a cada 15 segundos
  public void checkOtherAppHealth() {
    try {
      Book book = new Book(null, "Titulo "+ count++, "Autor "+ count);
      Book response = restTemplate.postForObject(URL, book, Book.class);

      logger.info("create book: {}", response);

      restTemplate.delete(URL+"/"+book.getId());

      logger.info("Deleted book {}", book);
    } catch (Exception e) {
      logger.error("Error ao create/delete book : {}", e.getMessage());
    }
  }
}
