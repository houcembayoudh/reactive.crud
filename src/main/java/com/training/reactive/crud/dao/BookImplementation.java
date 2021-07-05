package com.training.reactive.crud.dao;

import com.training.reactive.crud.exception.BookAlreadyExistsException;
import com.training.reactive.crud.exception.BookException;
import com.training.reactive.crud.exception.BookNameIsNotValidException;
import com.training.reactive.crud.exception.BookReferenceIsNotValidException;
import com.training.reactive.crud.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class BookImplementation implements BookRepository{

    List<Book> bookList = new ArrayList<>();

    @Override
    public Flux<Book> fetchAllBooks() {
        return Flux.fromIterable(bookList);
    }

    @Override
    public Flux<Book> getBookByReference(String reference) {
        return Flux.fromIterable(bookList)
                .filter(s -> s.getReference().equals(reference));
    }

    @Override
    public Mono<BookSavingRecord> addBook(Book book) {
        var containExceptions = checkBookValidity(book);
        if (containExceptions.isPresent()) {
            return
                    Mono.just(new BookSavingRecord(
                            book,
                            containExceptions.get()
                    ));
        }
        bookList.add(book);
        return
                Mono.just( new BookSavingRecord(
                        book
                ));
    }
/*
    @Override
    public Mono<SavingBooksRecord> saveAll(Collection<Book> books) {

        final var savingResults =
                books
                        .stream()
                        .map(this::addBook)// never parallelize this stream, it is not pure

                        .collect(
                                Collectors.partitioningBy(
                                        bookSavingRecord -> bookSavingRecord. bookSavingRecord.savingException().isEmpty()
                                )
                        );

        final var savedBooks =
                savingResults.get(true)
                        .stream()
                        .map(BookSavingRecord::book)
                        .collect(Collectors.toUnmodifiableList());

        final var notSavedBooks =
                savingResults.get(false)
                        .stream()
                        .filter(it -> it.savingException().isPresent())
                        .map(it ->
                                new SavingBooksRecord.UnsavedBook(
                                        it.book(),
                                        it.savingException()
                                                .get()
                                )
                        )
                        .collect(Collectors.toUnmodifiableList());

        return
                new SavingBooksRecord(
                        savedBooks,
                        notSavedBooks
                );

    }
*/

    private final boolean invalidBookName(String bookName) {
        return Objects.isNull(bookName) || bookName.isEmpty();
    }

    private final boolean invalidBookReference(String bookReference) {
        return Objects.isNull(bookReference) || bookReference.isEmpty();
    }

    private final Mono<Boolean> bookAlreadyExists(Book book) {
        var y = getBookByReference(book.getReference()).hasElement(book);
        return y;
    }
    private Optional<? extends BookException> checkBookValidity(Book book) {
        //validName
        if (invalidBookName(book.getBookName())) {
            return Optional.ofNullable(
                    new BookNameIsNotValidException
                            ("Book Name is Either null or empty"));
        }
        //validReference
        if (invalidBookReference(book.getReference())) {
            return Optional.ofNullable(
                    new BookReferenceIsNotValidException
                            ("Book Reference is Either null or empty"));
        }
        //bookExists
        if (bookAlreadyExists(book).block()) {
            return Optional.ofNullable(
                    new BookAlreadyExistsException
                            ("Book Already exists"));
        }
        return Optional.empty();
    }
}
