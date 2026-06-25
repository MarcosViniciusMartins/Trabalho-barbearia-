package com.barbearia.domain.model;

public class Cliente {

    private final String id;
    private final String nome;
    private final String telefone;

    public Cliente(String id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
}
