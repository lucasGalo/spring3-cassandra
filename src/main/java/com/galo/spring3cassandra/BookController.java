package com.galo.spring3cassandra;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookRepository repo;

  public BookController(BookRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<Book> getBooks() {
    return repo.findAll();
  }

  @PostMapping
  public Book addBook(@RequestBody Book book) {
    book.setId(UUID.randomUUID());
    return repo.save(book);
  }

  @DeleteMapping(path = "/{id}")
  public Book rmBook(@PathVariable UUID id) {
    Book book = repo.findById(id).orElse(null);
    if (book != null) {
      repo.delete(book);
    }
    return book;
  }
}
