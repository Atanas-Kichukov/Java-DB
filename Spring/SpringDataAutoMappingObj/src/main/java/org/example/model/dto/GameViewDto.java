package org.example.model.dto;

public class GameViewDto {
    private String title;

    public GameViewDto() {
    }

    public GameViewDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
