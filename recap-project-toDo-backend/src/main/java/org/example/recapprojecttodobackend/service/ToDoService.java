package org.example.recapprojecttodobackend.service;

import lombok.AllArgsConstructor;
import org.example.recapprojecttodobackend.model.ToDoDTO;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepo toDoRepo;

    public List<ToDoDTO> getAllToDos() {
        return toDoRepo.findAll().stream()
                .map(toDo -> {
                    ToDoDTO toDoDTO = new ToDoDTO(
                            toDo.description(),
                            toDo.toDoStatus()
                    );
                    return toDoDTO;
                })
                .toList();
    }

}
