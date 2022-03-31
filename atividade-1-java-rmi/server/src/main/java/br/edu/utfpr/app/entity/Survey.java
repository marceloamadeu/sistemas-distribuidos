package br.edu.utfpr.app.entity;

import java.time.LocalDateTime;

public class Survey {

    private int id;
    private String name;
    private String title;
    private String local;
    private LocalDateTime dateTime;
    private LocalDateTime finalDate;

    public Survey() {

    }

    public Survey(String name, String title, String local, 
    LocalDateTime dateTime, LocalDateTime finalDate) {
        this.name = name;
        this.title = title;
        this.local = local;
        this.dateTime = dateTime;
        this.finalDate = finalDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }

}
