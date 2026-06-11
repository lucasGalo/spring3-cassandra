package com.galo.spring3cassandra;


import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

//@Configuration
public class CassandraConfig {

//  @Bean
  public CqlSession cqlSession() {
    return CqlSession.builder()
            .addContactPoint(new InetSocketAddress("192.168.0.100", 9042)) // seu host Cassandra
            .withLocalDatacenter("datacenter1") // nome exato do DC
            .withKeyspace("mykeyspace")       // opcional, se quiser já conectar no keyspace
            .build();
  }
}
