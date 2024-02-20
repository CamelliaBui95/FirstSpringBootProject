package fr.btn.SpingDbProject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.btn.SpingDbProject.TestDataUtils;
import fr.btn.SpingDbProject.domain.dto.AuthorDto;
import fr.btn.SpingDbProject.domain.entities.AuthorEntity;
import fr.btn.SpingDbProject.services.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.getAuthorC();
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.getAuthorC();
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Tracy Chevalier")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(61)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity authorC = TestDataUtils.getAuthorC();
        authorService.save(authorC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Tracy Chevalier")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(61)
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception {
        AuthorEntity testAuthorA = TestDataUtils.getAuthorA();
        authorService.save(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorReturnsAuthorWhenAuthorExist() throws Exception {
        AuthorEntity testAuthorA = TestDataUtils.getAuthorA();
        authorService.save(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Carson McCullers")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(50)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception {
        AuthorDto authorDtoA = TestDataUtils.getAuthorDtoA();
        String authorAJson = objectMapper.writeValueAsString(authorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        authorService.save(TestDataUtils.getAuthorA());

        AuthorDto authorDtoA = TestDataUtils.getAuthorDtoA();
        String authorAJson = objectMapper.writeValueAsString(authorDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorAJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity savedAuthor = authorService.save(TestDataUtils.getAuthorA());

        AuthorDto authorDtoB = TestDataUtils.getAuthorDtoB();
        authorDtoB.setId(savedAuthor.getId());

        String authorDtoJson = objectMapper.writeValueAsString(authorDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + authorDtoB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(authorDtoB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorDtoB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorDtoB.getAge())
        );
    }
}
