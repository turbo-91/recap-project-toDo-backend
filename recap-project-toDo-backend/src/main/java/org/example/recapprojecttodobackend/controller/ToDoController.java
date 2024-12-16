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
    public List<ToDoDTO> getAll() {
        return toDoService.getAllToDos();
    }

    @PostMapping("/todo")
    public ToDoDTO createToDo(@RequestBody ToDo toDo) {
        return toDoService.createToDo(toDo);
    }

    @GetMapping("/{id}")
    public ToDoDTO getById(@PathVariable String id) {
        return toDoService.getById(id);
    }


}
