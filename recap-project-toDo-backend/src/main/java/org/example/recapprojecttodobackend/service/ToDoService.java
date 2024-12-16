package org.example.recapprojecttodobackend.service;

import lombok.AllArgsConstructor;
import org.example.recapprojecttodobackend.repo.ToDoRepo;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ToDoService {
    private final ToDoRepo todoRepo;
}
