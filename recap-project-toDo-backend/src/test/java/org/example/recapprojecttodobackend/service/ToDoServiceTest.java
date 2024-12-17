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
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ToDoServiceTest {

    private final ToDoRepo toDoRepo = mock(ToDoRepo.class);
    private final IdService idService= mock(IdService.class);

    // Tests getAllToDos

    @Test
    void getAllToDos_ShouldReturnEmptyList_whenCalledInitially() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService); // Service instance
        when(toDoRepo.findAll()).thenReturn(Collections.emptyList()); // Mock empty repository

        List<ToDo> expected = Collections.emptyList();

        //WHEN
        List<ToDo> actual = toDoService.getAllToDos();

        //THEN
        assertEquals(expected, actual); // Verify the returned list is empty
        verify(toDoRepo).findAll(); // Verify findAll was called once
    }

    @Test
    void getAllToDos_ShouldReturnListOfToDos_whenCalled() {
        //GIVEN
        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDo toDo2 = new ToDo("2", "Sign up for bootcamp", ToDo.toDoStatus.DONE);

        ToDoService toDoService = new ToDoService(toDoRepo, idService);
        List<ToDo> toDoList = List.of(toDo1, toDo2);

        when(toDoRepo.findAll()).thenReturn(toDoList); // Mock the repository to return the list

        List<ToDo> expected = toDoList;

        //WHEN
        List<ToDo> actual = toDoService.getAllToDos();

        //THEN
        assertEquals(expected, actual); // Verify the list matches
        verify(toDoRepo).findAll(); // Verify findAll was called once
    }

    @Test
    void getToDoById_ShouldReturnToDo_whenCalledWithValidId() {
        //GIVEN
        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDoService toDoService = new ToDoService(toDoRepo, idService);

        when(toDoRepo.findById("1")).thenReturn(Optional.of(toDo1)); // Mock repository returning the ToDo

        ToDo expected = toDo1;

        //WHEN
        ToDo actual = toDoService.getToDoById("1");

        //THEN
        assertEquals(expected, actual); // Verify the fetched ToDo matches the expected
        verify(toDoRepo).findById("1"); // Verify findById was called once
    }

    // createToDo test

    @Test
    void createToDo_shouldReturnCreatedToDo_whenCalledWithValidData() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService);
        ToDoDTO newToDo = new ToDoDTO("Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDo savedToDo = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        when(idService.generateId()).thenReturn("1"); // idService generates ID "1"
        when(toDoRepo.save(any(ToDo.class))).thenReturn(savedToDo);

        ToDo expected = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);

        //WHEN
        ToDo actual = toDoService.createToDo(newToDo);

        //THEN
        assertEquals(expected, actual); // Verify the returned object matches expectations
        verify(toDoRepo).save(any(ToDo.class)); // Verify save was called once
    }

    // updateToDo Tests

    @Test
    void updateToDo_shouldReturnUpdatedToDo_whenCalledWithValidData() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService);

        // Existing ToDo to update
        ToDo existingToDo = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDo updatedToDo = new ToDo("1", "Complete project", ToDo.toDoStatus.IN_PROGRESS);

        // Mock the repository behavior
        when(toDoRepo.existsById("1")).thenReturn(true);
        when(toDoRepo.save(updatedToDo)).thenReturn(updatedToDo);

        //WHEN
        ToDo actual = toDoService.updateTodo(updatedToDo);

        //THEN
        assertEquals(updatedToDo, actual); // Ensure the returned ToDo matches the updated one
        verify(toDoRepo).existsById("1"); // Verify ID existence check
        verify(toDoRepo).save(updatedToDo); // Verify save was called with the updated ToDo
    }

    // deleteToDo tests

    @Test
    void deleteToDo_shouldReturnDeletedToDo_whenCalledWithValidId() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService);

        // Existing ToDo to delete
        ToDo existingToDo = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);

        // Mock the repository behavior
        when(toDoRepo.existsById("1")).thenReturn(true);
        when(toDoRepo.findById("1")).thenReturn(Optional.of(existingToDo));

        //WHEN
        ToDo actual = toDoService.deleteTodo("1");

        //THEN
        assertEquals(existingToDo, actual); // Ensure the returned ToDo matches the deleted one
        verify(toDoRepo).existsById("1"); // Verify ID existence check
        verify(toDoRepo).findById("1"); // Verify findById was called
        verify(toDoRepo).deleteById("1"); // Verify deleteById was called
    }



}