package br.edu.utfpr.app.entity;

import java.io.Serializable;

public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String titulo;
    private String local;
    private String tempo;
    private String dataFinalEnquete;



    public Enquete() {
    }

    public Enquete(int id, String nome, String titulo, String local, String tempo, String dataFinalEnquete) {
        this.id = id;
        this.nome = nome;
        this.titulo = titulo;
        this.local = local;
        this.tempo = tempo;
        this.dataFinalEnquete = dataFinalEnquete;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTempo() {
        return this.tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDataFinalEnquete() {
        return this.dataFinalEnquete;
    }

    public void setDataFinalEnquete(String dataFinalEnquete) {
        this.dataFinalEnquete = dataFinalEnquete;
    }
}
