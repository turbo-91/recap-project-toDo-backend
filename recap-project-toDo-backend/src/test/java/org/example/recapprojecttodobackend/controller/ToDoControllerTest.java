package org.example.recapprojecttodobackend.controller;

import org.example.recapprojecttodobackend.model.ToDo;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepo toDoRepo;

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

}