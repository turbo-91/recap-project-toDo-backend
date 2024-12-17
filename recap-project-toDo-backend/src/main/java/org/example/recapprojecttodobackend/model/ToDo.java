package org.example.recapprojecttodobackend.model;

public record ToDo(String id, String description, toDoStatus status) {

    public enum toDoStatus {
        OPEN, IN_PROGRESS, DONE
    }

}
