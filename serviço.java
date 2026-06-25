package com.barbearia.domain.model;

import java.math.BigDecimal;
import java.time.Duration;

public class Servico {

    private final String id;
    private final String nome;
    private final Duration duracao;
    private final BigDecimal preco;

    public Servico(String id,
                   String nome,
                   Duration duracao,
                   BigDecimal preco) {

        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.preco = preco;
    }

    public Duration getDuracao() {
        return duracao;
    }

    public String getNome() {
        return nome;
    }
}
