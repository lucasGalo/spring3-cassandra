package com.galo.spring3cassandra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table("book")
public class Book {
  @PrimaryKey
  private UUID id;
  private String title;
  private String author;
}
