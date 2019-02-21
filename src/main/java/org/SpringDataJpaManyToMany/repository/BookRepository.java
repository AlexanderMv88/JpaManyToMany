package org.SpringDataJpaManyToMany.repository;

import org.SpringDataJpaManyToMany.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findOneByName(String name);
}
