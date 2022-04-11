package br.edu.utfpr.app.entity;

import java.io.Serializable;

public class EnqueteVoto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private int idUsuario;
    private int idEnquete;
    private int voto;
    private boolean subscriber = true;


    public EnqueteVoto(int id, int idUsuario, int idEnquete, int voto, boolean subscriber) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idEnquete = idEnquete;
        this.voto = voto;
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

    public boolean getSubscriber() {
        return this.subscriber;
    }


    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEnquete() {
        return this.idEnquete;
    }

    public void setIdEnquete(int idEnquete) {
        this.idEnquete = idEnquete;
    }

    public int getVoto() {
        return this.voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public boolean isSubscriber() {
        return this.subscriber;
    }


}
