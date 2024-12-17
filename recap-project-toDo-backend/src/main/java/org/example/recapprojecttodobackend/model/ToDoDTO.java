package org.example.recapprojecttodobackend.model;

public record ToDoDTO(String id, String description, ToDo.toDoStatus status) {

    public enum toDoStatus {
        OPEN, IN_PROGRESS, DONE
    }

}
