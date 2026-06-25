package com.barbearia.domain.observer;

import com.barbearia.domain.model.Agendamento;

public class LogNotificador implements AgendamentoObserver {

    @Override
    public void notificar(Agendamento agendamento) {

        System.out.println(
                "[LOG] Novo agendamento: "
                        + agendamento.getCliente().getNome());
    }
}
