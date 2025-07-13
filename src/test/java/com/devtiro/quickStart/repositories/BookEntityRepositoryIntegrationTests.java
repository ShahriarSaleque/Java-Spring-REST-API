package com.devtiro.quickStart.repositories;

import com.devtiro.quickStart.TestDataUtil;
import com.devtiro.quickStart.domain.entities.AuthorEntity;
import com.devtiro.quickStart.domain.entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTests {
    private BookRepository underTest;
    private AuthorRepository ar;

    @Autowired
    public BookEntityRepositoryIntegrationTests(BookRepository underTest, AuthorRepository ar) {
        this.underTest = underTest;
        this.ar = ar;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        BookEntity bookEntity = TestDataUtil.getBookA(authorEntity);
        underTest.save(bookEntity);
        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getTitle()).isEqualTo(bookEntity.getTitle());
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        ar.save(authorEntity);
        BookEntity bookEntity1 = TestDataUtil.getBookA(authorEntity);

        underTest.save(bookEntity1);
        BookEntity bookEntity2 = TestDataUtil.getBookB(authorEntity);

        underTest.save(bookEntity2);
        BookEntity bookEntity3 = TestDataUtil.getBookC(authorEntity);
        underTest.save(bookEntity3);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).contains(bookEntity1, bookEntity2, bookEntity3);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        ar.save(authorEntity);

        BookEntity bookEntity1 = TestDataUtil.getBookA(authorEntity);

        underTest.save(bookEntity1);

        bookEntity1.setTitle("Updated Title");
        underTest.save(bookEntity1);
        assertThat(bookEntity1.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        ar.save(authorEntity);

        BookEntity bookEntity1 = TestDataUtil.getBookA(authorEntity);

        underTest.save(bookEntity1);

        underTest.deleteById(bookEntity1.getIsbn());
        Optional<BookEntity> result = underTest.findById(bookEntity1.getIsbn());

        assertThat(result).isEmpty();
    }
}
