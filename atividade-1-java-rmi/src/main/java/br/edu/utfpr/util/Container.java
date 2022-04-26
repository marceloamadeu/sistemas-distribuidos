package br.edu.utfpr.util;

import br.edu.utfpr.entity.Enquete;
import br.edu.utfpr.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private Enquete enquete;
    private List<Usuario> usersList;

    public Container() {
        enquete = new Enquete();
        usersList = new ArrayList<>();
    }

    public Container(Enquete enquete) {
        this.enquete = enquete;
    }

    public Enquete getEnquete() {
        return enquete;
    }

    public void setEnquete(Enquete enquete) {
        this.enquete = enquete;
    }

    public List<Usuario> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Usuario> usersList) {
        this.usersList = usersList;
    }

}
