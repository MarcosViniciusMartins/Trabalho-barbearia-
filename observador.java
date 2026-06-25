package com.barbearia.domain.observer;

import com.barbearia.domain.model.Agendamento;

public interface AgendamentoObserver {

    void notificar(Agendamento agendamento);
}
