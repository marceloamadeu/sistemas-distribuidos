package br.edu.utfpr.app.entity;

import java.io.Serializable;

public class SurveyVote implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private int idUser;
    private int idSurvey;
    private int vote;
    private boolean subscriber = true;

    public SurveyVote(int idUser, int idSurvey, int vote, boolean subscriber) {    
        this.idUser = idUser;
        this.idSurvey = idSurvey;
        this.vote = vote;
        this.subscriber = subscriber;
    }


    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdSurvey() {
        return this.idSurvey;
    }

    public void setIdSurvey(int idSurvey) {
        this.idSurvey = idSurvey;
    }

    public int getVote() {
        return this.vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public boolean isSubscriber() {
        return this.subscriber;
    }

    public boolean getSubscriber() {
        return this.subscriber;
    }

}
