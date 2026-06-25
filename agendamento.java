package com.barbearia.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Agendamento {

    private final String id;
    private final Cliente cliente;
    private final Servico servico;
    private final LocalDateTime inicio;
    private final String observacao;

    private StatusAgendamento status;

    public Agendamento(
            Cliente cliente,
            Servico servico,
            LocalDateTime inicio,
            String observacao) {

        this.id = UUID.randomUUID().toString();
        this.cliente = cliente;
        this.servico = servico;
        this.inicio = inicio;
        this.observacao = observacao;
        this.status = StatusAgendamento.CONFIRMADO;
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return inicio.plus(servico.getDuracao());
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void cancelar() {
        status = StatusAgendamento.CANCELADO;
    }

    @Override
    public String toString() {
        return cliente.getNome()
                + " - "
                + servico.getNome()
                + " - "
                + status;
    }
}
