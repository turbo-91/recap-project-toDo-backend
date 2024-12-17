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
                            toDo.id(),
                            toDo.description(),
                            toDo.status()
                    );
                    return toDoDTO;
                })
                .toList();
    }

    public ToDoDTO getById(String id) {
        ToDo toDoToSave = toDoRepo.findById(id).orElseThrow();
        System.out.println("toDoToSave in getById" + toDoToSave);
        ToDoDTO toDoDTO = new ToDoDTO(
                toDoToSave.id(),
                toDoToSave.description(),
                toDoToSave.status());
        return toDoDTO;
    }

    public ToDoDTO createToDo(ToDo toDo) {
        String generatedId = idService.generateId();
//         Set toDoStatus by default if it is null
        ToDo.toDoStatus status = ToDo.toDoStatus.OPEN;

        ToDo toDoToSave = new ToDo(
                generatedId,
                toDo.description(),
                status
        );
        System.out.println("todo" + toDoToSave);
        ToDo savedToDo = toDoRepo.save(toDoToSave);
        ToDoDTO toDoDTOToReturn = new ToDoDTO(
                toDoToSave.id(),
                toDoToSave.description(),
                toDoToSave.status());
        System.out.println("todoDTO" + toDoToSave);
        return toDoDTOToReturn;
    }

    public ToDo updateToDo(ToDo toDo, String id) {
        if (toDoRepo.existsById(id)) {
            toDoRepo.save(toDo);
            return toDoRepo.findById(id).orElseThrow();
        }else {
            throw new RuntimeException("ToDo not found");
        }
    }

    public void deleteToDo(String id) {
        if (toDoRepo.existsById(id)) {
            toDoRepo.deleteById(id);
        }else {
            throw new RuntimeException("Apple not found");
        }
    }

}
