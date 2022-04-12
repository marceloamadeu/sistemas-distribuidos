package br.edu.utfpr.app.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String titulo;
    private String local;
    private String tempo;
    private String dataFinalEnquete;
    private LinkedHashSet<Integer> toBeNotified;



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


    /**
	 * 
	 * @return iterator to allow for removal of Subscribers as they are notified one by one about this event
	 */
	public synchronized Iterator<Integer> iterator() {
		return toBeNotified.iterator();
	}

	/**
	 * 
	 * @return number of users left to notify
	 */
	public synchronized int notifySize() {
		return toBeNotified.size();
	}

    /**
	 * 
	 * @param c some other list of subscribers (either content or topic filtering) to be added to the 
	 * list of subscribers of this event
	 * @return true/false that the list was successfully added
	 */
	public synchronized boolean addSubscriberList(Collection<Integer> c) {
		if (c != null)
			return toBeNotified.addAll(c);
		return false;
	}
}