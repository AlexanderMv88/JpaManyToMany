package org.SpringDataJpaManyToMany;

import org.SpringDataJpaManyToMany.entity.Author;
import org.SpringDataJpaManyToMany.entity.Book;
import org.SpringDataJpaManyToMany.repository.AuthorRepository;
import org.SpringDataJpaManyToMany.repository.BookRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringDataJpaManyToManyApplicationTests {

	@Autowired
	BookRepository bookRepository;
	@Autowired
	AuthorRepository authorRepository;

	@Test
	public void test1JpaCreate() {
		//Insert
		Stream.of(new Author("Пушкин"), new Author("Достоевский"), new Author("Тургенев"))
				.forEach(obj -> {
					authorRepository.saveAndFlush(obj);
				});
		List<Author> authors = authorRepository.findAll();
		assertThat(authors != null).isTrue();
		assertThat(authors.size() == 3).isTrue();

		Stream.of(new Book("Сборник рассказов",
					new HashSet<Author>(){{
						add(authors.get(0));
						add(authors.get(2));
					}}),
				  new Book("Преступление и наказание",
					new HashSet<Author>(){{
						add(authors.get(1));
					}}),
				new Book("Сказки",
						new HashSet<Author>(){{
							add(authors.get(0));
						}})
					)
				.forEach(obj -> {
					bookRepository.save(obj);
				});
		List<Book> books = bookRepository.findAll();
		assertThat(books != null).isTrue();
		assertThat(books.size() == 3).isTrue();
	}

	@Test
	public void test2JpaFind() {
		//Select
		Book book = bookRepository.findOneByName("Сборник рассказов");
		assertThat(book.getName()).isEqualTo("Сборник рассказов");
		assertThat(book.getAuthors().size() == 2);
		assertThat(book.getAuthors().contains(authorRepository.getOneByName("Пушкин")));
		assertThat(book.getAuthors().contains(authorRepository.getOneByName("Тургенев")));

		Author author = authorRepository.getOneByName("Пушкин");
		assertThat(author.getBooks().size() == 2);
		assertThat(author.getBooks().contains(bookRepository.findOneByName("Сборник рассказов")));
		assertThat(author.getBooks().contains(bookRepository.findOneByName("Сказки")));
	}

	@Test
	public void test3JpaChange() {
		//Update
		Book book = bookRepository.findOneByName("Сборник рассказов");
		book.setName("Сборник рассказов и сказок");
		Author author = new Author("Чехов");
		authorRepository.save(author);
		book.addAuthor(author);
		bookRepository.save(book);
		Book changedBook = bookRepository.findOneByName("Сборник рассказов и сказок");
		assertThat(changedBook).isEqualTo(book);
	}

	@Test
	public void test4JpaDelete() {
		List<Book> booksForDelete = bookRepository.findAll();
		bookRepository.deleteAll(booksForDelete);
		assertThat(bookRepository.findAll().size() == 0).isTrue();

		List<Author> authorsForDelete = authorRepository.findAll();
		authorRepository.deleteAll(authorsForDelete);
		assertThat(authorRepository.findAll().size() == 0).isTrue();
	}
}
