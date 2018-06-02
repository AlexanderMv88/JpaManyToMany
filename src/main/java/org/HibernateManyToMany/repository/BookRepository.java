package org.HibernateManyToMany.repository;

import org.HibernateManyToMany.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findOneByName(String name);
}
