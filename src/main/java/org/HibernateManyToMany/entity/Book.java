package org.HibernateManyToMany.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", length = 255)
    private String name;
    //CascadeType.ALL => CascadeType.MERGE решает проблему org.hibernate.PersistentObjectException: detached entity
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private Set<Author> authors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author){
        this.authors.add(author);
    }

    public Book(String name, Set<Author> authors) {
        this.name = name;
        this.authors = authors;
    }

    public Book() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, authors);
    }
}
