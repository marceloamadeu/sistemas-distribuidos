package br.edu.utfpr.app.entity;

import java.io.Serializable;

public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String title;
    private String local;
    private String dateTime;
    private String finalDate;

    public Survey() {

    }

    public Survey(String name, String title, String local, 
    String dateTime, String finalDate) {
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

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFinalDate() {
        return this.finalDate;
    }

    public void setFinalDate(String finalDate) {
        this.finalDate = finalDate;
    }

}
