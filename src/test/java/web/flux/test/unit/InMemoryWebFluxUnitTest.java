package web.flux.test.unit;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.training.reactive.crud.dao.BookImplementation;
import com.training.reactive.crud.dao.BookRepository;
import com.training.reactive.crud.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InMemoryWebFluxUnitTest {


    private BookRepository bookRepository;
    public InMemoryWebFluxUnitTest() {
        bookRepository = new BookImplementation();
    }
    @Test
    public void getBookByReferenceUnitTest(){
        List<Book> bookList = List.of(new Book("Omar", "Sportage"),
                new Book("Houcem", "Octavia"),
                new Book("Monta","el 11"));

        Flux.fromIterable(bookList)
                .flatMap(book1 -> {
                    return Flux.fromIterable(bookList);
                })
            .subscribe(System.out::println);



      /*  StepVerifier.create(bookFlux.log())
                .expectNext(new Book("Omar", "Sportage"))
                .verifyComplete();*/
    }

    @Test
    public void addBookUnitTest(){
       final Book book = new Book("Omar", "Sportage");
       final var bookAdded =  bookRepository.addBook(book);
       final var resultBook = bookAdded.log();
        System.out.println("resultBook = " + resultBook);
        final var expected = new BookRepository
                .SavingBooksRecord(
                List.of(book) ,
                List.of()
        );

        System.out.println("expected = " + resultBook.block());
        System.out.println("expected.getSavedBooks() = " + expected.getSavedBooks());
        /*StepVerifier.create(resultBook)
                .expectNext( expected.getSavedBooks())
                .verifyComplete();*/
    }
}
