package br.edu.utfpr.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id = 0;
    private String nome;
    private String titulo;
    private String local;
    private String tempo;
    private String dataFinalEnquete;


    public Enquete() {
    }

    public Enquete(String nome, String titulo, String local, String tempo, String dataFinalEnquete) {        
        this.nome = nome;
        this.titulo = titulo;
        this.local = local;
        this.tempo = tempo;
        this.dataFinalEnquete = dataFinalEnquete;
    }


    public int getId() {
        return this.id;
    }

    public Enquete setId(int id) {
        this.id = id;
        return this;
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