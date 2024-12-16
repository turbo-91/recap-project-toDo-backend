package org.example.recapprojecttodobackend.model;

public record ToDo(String id, String description, toDoStatus toDoStatus) {

    public enum toDoStatus {
        TODO, DOING, DONE
    }

}
