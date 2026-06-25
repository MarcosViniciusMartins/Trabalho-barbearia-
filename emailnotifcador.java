package com.barbearia.domain.observer;

import com.barbearia.domain.model.Agendamento;

public class EmailNotificador implements AgendamentoObserver {

    @Override
    public void notificar(Agendamento agendamento) {

        System.out.println(
                "[EMAIL] confirmação enviada para "
                        + agendamento.getCliente().getNome());
    }
}
