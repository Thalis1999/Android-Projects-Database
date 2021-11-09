package com.example.bancodedados;

public class Produto {
    private int _id;
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(int _id, String nome, double preco, int quantidade) {
        this._id = _id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int get_id() {
        return _id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + " Preço: R$ " + preco + " Quantidade: " + quantidade;
    }
}
