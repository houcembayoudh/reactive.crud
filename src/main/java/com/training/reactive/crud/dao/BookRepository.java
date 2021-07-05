package com.training.reactive.crud.dao;

import com.training.reactive.crud.exception.BookException;
import com.training.reactive.crud.model.Book;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;


public interface BookRepository {

    public Flux<Book> fetchAllBooks();

    public Flux<Book> getBookByReference(String reference);

    public Mono<BookSavingRecord> addBook(Book book);
   // public Mono<SavingBooksRecord> saveAll(Collection<Book> books);


    @Data
    public static final class SavingBooksRecord {
        private final Collection<Book> savedBooks;
        private final Collection<UnsavedBook> unsavedBooks;

        @Data
        public static final class UnsavedBook {
            private final Book book;
            private final BookException reason;
        }
    }

    public static final class BookSavingRecord {
        private final Book book;
        private final BookException savingException;

        public BookSavingRecord(final Book book) {
            this.book = book;
            this.savingException = null;
        }

        public BookSavingRecord(final Book book, final BookException savingException) {
            this.book = book;
            this.savingException = savingException;
        }

        public final Book book() {
            return this.book;
        }

        public final Optional<? extends BookException> savingException() {
            return
                    Objects.nonNull(this.savingException)
                            ? Optional
                            .ofNullable(this.savingException)
                            : Optional.empty();
        }
    }
}
