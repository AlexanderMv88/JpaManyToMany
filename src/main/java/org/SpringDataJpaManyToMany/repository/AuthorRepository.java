package org.SpringDataJpaManyToMany.repository;

import org.SpringDataJpaManyToMany.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getOneByName(String name);
}
