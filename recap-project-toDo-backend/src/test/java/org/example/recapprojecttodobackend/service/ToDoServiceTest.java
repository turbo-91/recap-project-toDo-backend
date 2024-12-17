package org.example.recapprojecttodobackend.service;

import org.example.recapprojecttodobackend.model.ToDo;
import org.example.recapprojecttodobackend.model.ToDoDTO;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoServiceTest {

    private final ToDoRepo toDoRepo = mock(ToDoRepo.class);
    private final IdService idService= mock(IdService.class);

    // Tests getAllToDos

    @Test
    void getAllToDos_ShouldReturnEmptyList_whenCalledInitially() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService); // empty by default

        List<ToDoDTO> expected = Collections.emptyList();
        //WHEN
        List<ToDoDTO> actual = toDoService.getAllToDos();
        //THEN
        assertEquals(expected, actual);
    }

//    @Test
//    void getAllToDos_ShouldReturnListOfToDoDTOs_whenCalled() {
//        //GIVEN
//        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.TODO);
//        ToDo toDo2 = new ToDo("2", "Sign up for bootcamp", ToDo.toDoStatus.DONE);
//        ToDoDTO toDoDTO1 = new ToDoDTO(null, "Finish bootcamp", ToDo.toDoStatus.TODO);
//        ToDoDTO toDoDTO2 = new ToDoDTO(null, "Sign up for bootcamp", ToDo.toDoStatus.DONE);
//        ToDoService toDoService = new ToDoService(toDoRepo, idService);
//        List<ToDo> toDoList = List.of(toDo1, toDo2);
//        when(toDoRepo.findAll()).thenReturn(toDoList);
//        List<ToDoDTO> expected = List.of(toDoDTO1, toDoDTO2);
//        //WHEN
//        List<ToDoDTO> actual = toDoService.getAllToDos();
//        //THEN
//        assertEquals(expected, actual);
//    }

    // GetById Tests
//    @Test
//    void getById_shouldReturnToDoDTO1_whenCalledWithValidId() {
//        //GIVEN
//        ToDo toDo1= new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.TODO);
//        ToDoService toDoService = new ToDoService(toDoRepo, idService);
//        when(toDoRepo.findById(toDo1.id())).thenReturn(Optional.of(toDo1)); // mocking that the figure is being found from the repo
//
//        ToDoDTO expected = new ToDoDTO(null, toDo1.description(), toDo1.toDoStatus());
//        //WHEN
//        ToDoDTO actual = figureService.getById(figure.id());
//        //THEN
//        assertEquals(expected, actual);
//    }

}