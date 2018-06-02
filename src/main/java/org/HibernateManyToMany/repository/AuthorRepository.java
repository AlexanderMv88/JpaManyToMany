package org.HibernateManyToMany.repository;

import org.HibernateManyToMany.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author getOneByName(String name);
}
