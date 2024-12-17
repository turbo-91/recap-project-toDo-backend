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
        ToDoService toDoService = new ToDoService(toDoRepo, idService); // empty by default

        List<ToDoDTO> expected = Collections.emptyList();
        //WHEN
        List<ToDoDTO> actual = toDoService.getAllToDos();
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void getAllToDos_ShouldReturnListOfToDoDTOs_whenCalled() {
        //GIVEN
        ToDo toDo1 = new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDo toDo2 = new ToDo("2", "Sign up for bootcamp", ToDo.toDoStatus.DONE);
        ToDoDTO toDoDTO1 = new ToDoDTO("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDoDTO toDoDTO2 = new ToDoDTO("2", "Sign up for bootcamp", ToDo.toDoStatus.DONE);
        ToDoService toDoService = new ToDoService(toDoRepo, idService);
        List<ToDo> toDoList = List.of(toDo1, toDo2);
        when(toDoRepo.findAll()).thenReturn(toDoList);
        List<ToDoDTO> expected = List.of(toDoDTO1, toDoDTO2);
        //WHEN
        List<ToDoDTO> actual = toDoService.getAllToDos();
        //THEN
        assertEquals(expected, actual);
    }

    // GetById Tests
    @Test
    void getById_shouldReturnToDoDTO1_whenCalledWithValidId() {
        //GIVEN
        ToDo toDo1= new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDoService toDoService = new ToDoService(toDoRepo, idService);
        when(toDoRepo.findById(toDo1.id())).thenReturn(Optional.of(toDo1)); // mocking that the figure is being found from the repo

        ToDoDTO expected = new ToDoDTO("1", toDo1.description(), toDo1.status());
        //WHEN
        ToDoDTO actual = toDoService.getById(toDo1.id());
        //THEN
        assertEquals(expected, actual);
    }

    // createToDo test

    @Test
    void createFigure_shouldReturnCreatedFigure_whenCalledWithValidData() {
        //GIVEN
        ToDoService toDoService = new ToDoService(toDoRepo, idService);
        ToDo toDo1= new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        when(idService.generateId()).thenReturn("1"); // mocking that the idService returns our id
        when(toDoRepo.save(toDo1)).thenReturn(toDo1); // mocking that the figureRepo saves succesfully and then returns toDo

        ToDoDTO expected = new ToDoDTO("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);

        //WHEN
        ToDoDTO actual = toDoService.createToDo(toDo1);

        //THEN
        assertEquals(expected, actual);
        verify(toDoRepo).save(toDo1); // additionally verifying that save has not only been mocked (above) but also called once
    }

    // updateToDo Tests

    @Test
    void updateFigure_shouldReturnUpdatedAsterix_whenCalledWithValidData() {
        // GIVEN
        ToDo toDo1= new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        ToDoService toDoService = new ToDoService(toDoRepo, idService); // siehe oben
        when(toDoRepo.existsById(toDo1.id())).thenReturn(true); // mocking the fulfilled if-condition
        when(toDoRepo.findById(toDo1.id())).thenReturn(Optional.of(toDo1)); // mocking that statement of condition returns optional of figure

        ToDo expected = new ToDo(
                toDo1.id(),
                toDo1.description(),
                toDo1.status());

        // WHEN
        ToDo actual = toDoService.updateToDo(toDo1, toDo1.id());

        //THEN
        assertEquals(expected, actual);
        verify(toDoRepo).save(toDo1); // verifying if figureRepo.save(figure) is called once in updateFigure
        // which is sufficient as we do not need to check if figureRepo.save works
        // -> that's a test for the figureRepo tests
    }

    // deleteToDo tests

    @Test
    void testDeleteFigure_GivenValidId_WhenExists_ThenDeletesSuccessfully() {
        // GIVEN
        ToDo toDo1= new ToDo("1", "Finish bootcamp", ToDo.toDoStatus.OPEN);
        when(toDoRepo.existsById(toDo1.id())).thenReturn(true);
        ToDoService toDoService = new ToDoService(toDoRepo, idService);

        // WHEN
        toDoService.deleteToDo(toDo1.id());

        // THEN
        verify(toDoRepo).deleteById(toDo1.id()); // verify deleteById was called once
    }



}