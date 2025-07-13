package com.devtiro.quickStart.repositories;

import com.devtiro.quickStart.TestDataUtil;
import com.devtiro.quickStart.domain.entities.AuthorEntity;
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
public class AuthorEntityRepositoryIntegrationTest {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository underTest) {
            this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled () {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();

        underTest.save(authorEntity);
        Optional<AuthorEntity> result =  underTest.findById(authorEntity.getId());

        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(authorEntity);

    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity1 = TestDataUtil.getAuthorEntityA();
        underTest.save(authorEntity1);
        AuthorEntity authorEntity2 = TestDataUtil.getAuthorB();
        underTest.save(authorEntity2);
        AuthorEntity authorEntity3 = TestDataUtil.getAuthorC();
        underTest.save(authorEntity3);

        Iterable<AuthorEntity> result = underTest.findAll();

        assertThat(result).hasSize(3).contains(authorEntity1, authorEntity2, authorEntity3);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        underTest.save(authorEntity);
        authorEntity.setName("Updated Saleque");
        underTest.save(authorEntity);

        assertThat(authorEntity.getName()).isEqualTo("Updated Saleque");
    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.getAuthorEntityA();
        underTest.save(authorEntity);
        underTest.deleteById(authorEntity.getId());

        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorWithAgeLessThan() {
        AuthorEntity testAuthorEntityA = TestDataUtil.getAuthorEntityA();
        underTest.save(testAuthorEntityA);
        AuthorEntity testAuthorEntityB = TestDataUtil.getAuthorB();
        underTest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtil.getAuthorC();
        underTest.save(testAuthorEntityC);

        Iterable<AuthorEntity> results = underTest.ageLessThan(50);

        assertThat(results).containsExactly(testAuthorEntityB, testAuthorEntityC);
    }

    @Test
    public void testThatGetAuthorWithAgeGreaterThan() {
        AuthorEntity testAuthorEntityA = TestDataUtil.getAuthorEntityA();
        underTest.save(testAuthorEntityA);
        AuthorEntity testAuthorEntityB = TestDataUtil.getAuthorB();
        underTest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtil.getAuthorC();
        underTest.save(testAuthorEntityC);

        Iterable<AuthorEntity> results = underTest.findAuthorWithAgeGreaterThan(50);
        assertThat(results).containsExactly(testAuthorEntityA);
    };
}
