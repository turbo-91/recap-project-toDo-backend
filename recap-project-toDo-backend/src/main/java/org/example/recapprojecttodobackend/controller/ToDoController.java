package org.example.recapprojecttodobackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.recapprojecttodobackend.model.ToDoDTO;
import org.example.recapprojecttodobackend.service.ToDoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todoapp")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;


}
