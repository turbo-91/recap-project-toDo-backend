package org.example.recapprojecttodobackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.recapprojecttodobackend.model.ToDo;
import org.example.recapprojecttodobackend.model.ToDoDTO;
import org.example.recapprojecttodobackend.service.ToDoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/todo")
    public List<ToDo> getAll() {
        return toDoService.getAllToDos();
    }

    @GetMapping("/todo/{id}")
    public ToDo getToDoById(@PathVariable String id) {
        return toDoService.getToDoById(id);
    }

    @PostMapping
    public ToDo createTodo(@RequestBody ToDoDTO newToDo ){
        return toDoService.createToDo(newToDo);
    }

    @PutMapping("/todo/{id}")
    public ToDo updateToDo(@RequestBody ToDo updatedTodo) {
        return toDoService.updateTodo(updatedTodo);
    }

    @DeleteMapping("/{id}")
    public ToDo deleteTodo(@PathVariable String id){
        return toDoService.deleteTodo(id);
    }
}
