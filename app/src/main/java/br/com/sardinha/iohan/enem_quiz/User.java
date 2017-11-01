package br.com.sardinha.iohan.enem_quiz;


import java.io.Serializable;

public class User implements Serializable{

    private String id;
    private  String nome;
    private String email;
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User()
    {

    }

    public User(String id, String nome, String email)
    {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.admin = false;
    }


}
