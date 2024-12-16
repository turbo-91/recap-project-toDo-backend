package org.example.recapprojecttodobackend.model;

public record ToDoDTO(String description, ToDo.toDoStatus toDoStatus) {

    public enum toDoStatus {
        TODO, DOING, DONE
    }

}
