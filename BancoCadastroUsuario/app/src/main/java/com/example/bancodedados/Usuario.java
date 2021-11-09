package com.example.bancodedados;

public class Usuario {
    //Propriedades
    private int _id;
    private String nome;
    private String email;
    private String usuario;
    private String senha;

    //Construtor (Atalho: Alt + Insert)
    public Usuario(int _id, String nome, String email, String usuario, String senha) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + " E-mail: " + email + " Usuário: " + usuario;
    }

}
