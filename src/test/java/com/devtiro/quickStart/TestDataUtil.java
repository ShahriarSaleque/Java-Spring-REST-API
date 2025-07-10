package com.devtiro.quickStart;

import com.devtiro.quickStart.domain.entities.AuthorEntity;
import com.devtiro.quickStart.domain.entities.BookEntity;

public final class TestDataUtil {
    private TestDataUtil() {

    }

    public static AuthorEntity getAuthorA() {
        return AuthorEntity.builder()
                        .name("Shahriar Saleque")
                        .age(80)
                        .build();
    }

    public static AuthorEntity getAuthorB() {
        return AuthorEntity.builder()
                .name("Farhat Maliha")
                .age(44)
                .build();
    }

    public static AuthorEntity getAuthorC() {
        return AuthorEntity.builder()
                .name("Hussain Shahensha")
                .age(24)
                .build();
    }

    public static BookEntity getBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("Too Good To Be True")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity getBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-1231-12")
                .title("Too Good To Be False")
                .authorEntity(authorEntity)
                .build();
    }

    public static BookEntity getBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-2345-3312-2")
                .title("Too false To Be false")
                .authorEntity(authorEntity)
                .build();
    }
}
