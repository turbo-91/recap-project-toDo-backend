package org.example.recapprojecttodobackend.service;

import lombok.AllArgsConstructor;
import org.example.recapprojecttodobackend.model.ToDo;
import org.example.recapprojecttodobackend.model.ToDoDTO;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.MergeOperation.UniqueMergeId.id;

@AllArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepo toDoRepo;
    private final IdService idService;

    public List<ToDoDTO> getAllToDos() {
        return toDoRepo.findAll().stream()
                .map(toDo -> {
                    ToDoDTO toDoDTO = new ToDoDTO(
                            null,
                            toDo.description(),
                            toDo.toDoStatus()
                    );
                    return toDoDTO;
                })
                .toList();
    }

    public ToDoDTO getById(String id) {
        ToDo toDoToSave = toDoRepo.findById(id).orElseThrow();
        ToDoDTO toDoDTO = new ToDoDTO(
                null,
                toDoToSave.description(),
                toDoToSave.toDoStatus());
        return toDoDTO;
    }

    public ToDoDTO createToDo(ToDo toDo) {
        String generatedId = idService.generateId();
        ToDo toDoToSave = new ToDo(
                generatedId,           // Set the generated ID
                toDo.description(),    // Keep the description
                toDo.toDoStatus()      // Keep the status
        );
        ToDo savedToDo = toDoRepo.save(toDoToSave);
        ToDoDTO toDoDTOToReturn = new ToDoDTO(
                generatedId,
                toDoToSave.description(),
                toDoToSave.toDoStatus());
        return toDoDTOToReturn;
    }
}
