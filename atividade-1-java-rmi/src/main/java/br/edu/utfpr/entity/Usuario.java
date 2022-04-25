package br.edu.utfpr.entity;

import br.edu.utfpr.client.ClientImpl;
import br.edu.utfpr.interfaces.ClientInterface;

import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String chavePublica;
    private String referenciaObjetoRemoto;
    private ClientInterface client;

    public Usuario() {

    }

    public Usuario(int id, String nome, String chavePublica, String referenciaObjetoRemoto, ClientInterface client) {
        this.id = id;
        this.nome = nome;
        this.chavePublica = chavePublica;
        this.referenciaObjetoRemoto = referenciaObjetoRemoto;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChavePublica() {
        return chavePublica;
    }

    public void setChavePublica(String chavePublica) {
        this.chavePublica = chavePublica;
    }

    public String getReferenciaObjetoRemoto() {
        return referenciaObjetoRemoto;
    }

    public void setReferenciaObjetoRemoto(String referenciaObjetoRemoto) {
        this.referenciaObjetoRemoto = referenciaObjetoRemoto;
    }

    public ClientInterface getClient() {
        return client;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
