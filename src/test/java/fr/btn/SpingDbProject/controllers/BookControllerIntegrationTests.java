package fr.btn.SpingDbProject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.dto.BookDto;
import fr.btn.SpingDbProject.domain.entities.BookEntity;
import fr.btn.SpingDbProject.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto bookDto = TestDataUtils.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", bookDto.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsHttpStatus200Ok() throws Exception {
        BookEntity testBookEntityA = TestDataUtils.getBookA(null);
        BookEntity savedBookA = bookService.createUpdate(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtils.createBookDtoA(null);
        bookDto.setIsbn(savedBookA.getIsbn());

        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", bookDto.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatCreateBookReturnsSavedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtils.getBookA(null);
        BookEntity savedBookA = bookService.createUpdate(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto bookDto = TestDataUtils.createBookDtoB(null);
        bookDto.setIsbn(savedBookA.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", savedBookA.getIsbn()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookDto bookDto = TestDataUtils.createBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put(String.format("/books/%s", "123-458-102"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("123-458-102")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfBooks() throws Exception {
        BookEntity bookA = TestDataUtils.getBookA(null);
        bookService.createUpdate(bookA.getIsbn(), bookA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(bookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(bookA.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").isEmpty()
        );
    }

    @Test
    public void testThatBooksReturnsHttpStatus200WhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtils.getBookA(null);
        bookService.createUpdate(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatBooksReturnsHttpStatus404WhenBookDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/9780140181326")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatBooksReturnsBookWhenBookExists() throws Exception {
        BookEntity testBook = TestDataUtils.getBookA(null);
        bookService.createUpdate(testBook.getIsbn(), testBook);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBook.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").isEmpty()
        );
    }

}
