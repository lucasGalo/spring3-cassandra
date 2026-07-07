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
public class GatewayScheduler {

  static int count;

  private static final Logger logger = LoggerFactory.getLogger(GatewayScheduler.class);

  @Value("${apps.gateway.url}")
  public String URL_GATEWAY;
  private final RestTemplate restTemplate = new RestTemplate();


  @Scheduled(fixedRate = 10000) // a cada 30 segundos
  public void getUsersWithAuth() {
    try {
      String usersUrl = URL_GATEWAY + "/users";
      String response = restTemplate.getForObject(usersUrl, String.class);
      logger.info("Users retrieved successfully: {}", response);
    } catch (HttpClientErrorException.Unauthorized e) {
      logger.warn("Received 401 Unauthorized. Attempting to authenticate...");
      String token = authenticateAndGetToken();

      if (token != null && !token.isEmpty()) {
        try {
          HttpHeaders headers = new HttpHeaders();
          headers.set("Authorization", "Bearer " + token);
          HttpEntity<String> entity = new HttpEntity<>(headers);

          ResponseEntity<String> response = restTemplate.exchange(
                  URL_GATEWAY + "/users",
                  HttpMethod.GET,
                  entity,
                  String.class
          );
          logger.info("Users retrieved with token: {}", response.getBody());
        } catch (Exception ex) {
          logger.error("Error retrieving users with token: {}", ex.getMessage());
        }
      } else {
        logger.error("Failed to obtain authentication token");
      }
    } catch (Exception e) {
      logger.error("Error retrieving users: {}", e.getMessage());
    }
  }

  private String authenticateAndGetToken() {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Content-Type", "application/json");

      Map<String, String> credentials = new HashMap<>();
      credentials.put("username", "admin");
      credentials.put("password", "123");

      ObjectMapper mapper = new ObjectMapper();
      String jsonPayload = mapper.writeValueAsString(credentials);

      HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

      ResponseEntity<String> response = restTemplate.postForEntity(
              URL_GATEWAY + "/auth/login",
              request,
              String.class
      );

      if (response.getStatusCode() == HttpStatus.OK) {
        Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);
        String token = (String) responseMap.get("accessToken");

        if (token != null) {
          logger.info("Token obtained successfully");
          return token;
        }
      }
    } catch (Exception e) {
      logger.error("Error during authentication: {}", e.getMessage());
    }

    return null;
  }

}
