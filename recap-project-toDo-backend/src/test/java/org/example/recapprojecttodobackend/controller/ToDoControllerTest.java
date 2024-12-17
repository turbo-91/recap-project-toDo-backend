package org.example.recapprojecttodobackend.controller;

import org.example.recapprojecttodobackend.model.ToDo;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.example.recapprojecttodobackend.service.IdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepo toDoRepo;

    @Autowired
    private IdService idService;

    @Test
    void getAll_shouldReturnEmptyList_whenCalledInitially() throws Exception {
        //GIVEN

        // THEN & WHEN
        mockMvc.perform(get("/api/todo")) // mockMvC simuliert get Anfrage an path
                .andExpect(status().isOk()) // expect the status code in the response is 200 (OK)
                .andExpect(content().json("[]")); // expect empty array as response as before initialization
    }

    @Test
    void getById_shouldReturnToDo1_whenCalledWithValidId() throws Exception {
        //GIVEN
        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        toDoRepo.save(toDo1); // and save it in the repo
        //WHEN & THEN
        mockMvc.perform(get("/api/todo/" + toDo1.id())) // mockMvc performs / mocks / simulates GET request to path
                .andExpect(status().isOk()) // expect response status 200 (OK)
                .andExpect(content().json("""
                                       {
                                         "id": "1",
                                         "description": "Finish bootcamp",
                                         "status": "OPEN"
                                        }
                                        """));  // expect this json response
    }

        @Test
        void createFigure_shouldReturnToDo1AsDTO_whenCalledWithToDo1() throws Exception {
            // Perform POST request without specifying the ID
            mockMvc.perform(post("/api/todo")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""" 
                            {
                                "description": "Finish bootcamp",
                                "status": "OPEN"
                            }
                            """))
                    .andExpect(status().isOk()) // Expect status 200 OK
                    .andExpect(jsonPath("$.id").isNotEmpty()) // Expect the ID field to be generated and not empty
                    .andExpect(jsonPath("$.description").value("Finish bootcamp")) // Validate description
                    .andExpect(jsonPath("$.status").value("OPEN")); // Validate status
        }

    @Test
    void updateToDo_shouldUpdateAndReturnUpdatedToDo_whenCalledWithValidId() throws Exception {
        // GIVEN: Save an initial ToDo object in the repo
        ToDo existingToDo = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        toDoRepo.save(existingToDo);

        // Updated content
        String updatedContent = """
                {
                    "id": "1",
                    "description": "Updated description",
                    "status": "OPEN"
                }
                """;

        // WHEN & THEN: Perform PUT request and validate
        mockMvc.perform(put("/api/todo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedContent))
                .andExpect(status().isOk()) // Expect status 200 OK
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void deleteToDo_shouldDeleteToDo_whenCalledWithValidId () throws Exception {
        // GIVEN
        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        toDoRepo.save(toDo1);
        // WHEN & THEN
        assertTrue(toDoRepo.existsById(toDo1.id()));
        mockMvc.perform(delete("/api/todo/" + toDo1.id()))
                .andExpect(status().isOk());
        assertFalse(toDoRepo.existsById(toDo1.id()));
    }
}