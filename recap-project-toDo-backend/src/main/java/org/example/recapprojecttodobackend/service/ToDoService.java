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

    public List<ToDo> getAllToDos() {
        return toDoRepo.findAll();
    }

    public ToDo getToDoById(String id) {
        return toDoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID: " + id));
    }

    public ToDo createToDo(ToDoDTO newToDo) {
        ToDo todo = new ToDo(
                idService.generateId(),
                newToDo.description(),
                newToDo.status()
        );
        return toDoRepo.save(todo);
    }

    public ToDo updateTodo(ToDo updatedToDo) {
        if (toDoRepo.existsById(updatedToDo.id())){
            return toDoRepo.save(updatedToDo);
        }else {
            throw new IllegalArgumentException("No user found with ID: " + updatedToDo.id());
        }
    }

    public ToDo deleteTodo(String id) {
        if (toDoRepo.existsById(id)){
            ToDo deletedTodo = toDoRepo.findById(id).orElseThrow();
            toDoRepo.deleteById(id);
            return deletedTodo;
        }else {
            throw new IllegalArgumentException("No user found with ID: " + id);
        }
    }

}
