package br.edu.utfpr.app.entity;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private int id;
    private String nome;
    private String chavePublica;
    private String objetoReferencia;
    private boolean subscriber = true;

    public Usuario(int id, String nome, String chavePublica, String objetoReferencia, boolean subscriber) {
        this.id = id;
        this.nome = nome;
        this.chavePublica = chavePublica;
        this.objetoReferencia = objetoReferencia;
        this.subscriber = subscriber;
    }



    public Usuario() {

    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChavePublica() {
        return this.chavePublica;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getObjetoReferencia() {
        return this.objetoReferencia;
    }

    public void setObjetoReferencia(String objetoReferencia) {
        this.objetoReferencia = objetoReferencia;
    }    

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSubscriber() {
        return this.subscriber;
    }

    public boolean getSubscriber() {
        return this.subscriber;
    }

    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
    }
    


   
}
