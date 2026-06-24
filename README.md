# Sistema de Agendamento — Barbearia

> Projeto da UC de Engenharia de Software — Back-end Java com SOLID e Design Patterns

---

## Contexto do Problema

Uma barbearia de bairro realiza todos os agendamentos de forma **totalmente manual**: o barbeiro anota horários em um caderno ou gerencia via WhatsApp. Isso causa:

- Conflitos de horário (duplo agendamento)
- Clientes sem visibilidade de disponibilidade
- Cancelamentos sem aviso prévio
- Ausência de histórico de atendimentos

## Solução Proposta

Sistema back-end em Java responsável por:

- Cadastro de clientes e serviços
- Criação de agendamentos com **verificação automática de conflitos**
- Cancelamento de agendamentos
- Notificação de mudanças de status (padrão Observer)
- Listagem de agenda por dia

---

## Tecnologias

| Item | Versão |
|------|--------|
| Java | 17+ |
| JUnit Jupiter | 5.10.1 |
| Mockito | 5.8.0 |
| Maven | 3.9+ |

---

## Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.9+

### Rodar os testes

```bash
mvn test
```

### Compilar o projeto

```bash
mvn compile
```

---

## Estrutura de Pacotes

```
src/
  main/java/com/barbearia/
    domain/
      model/         ← Agendamento (Builder), Cliente, Servico, StatusAgendamento
      repository/    ← AgendamentoRepository (interface)
      service/       ← AgendamentoService, LoggingAgendamentoService (Decorator)
      observer/      ← AgendamentoObserver, EmailNotificador, LogNotificador
      validator/     ← ConflictValidator (Strategy), HorarioConflictValidator
      exception/     ← ConflictException, AgendamentoNotFoundException
    infrastructure/
      repository/    ← InMemoryAgendamentoRepository
  test/java/com/barbearia/
    service/         ← AgendamentoServiceTest
    domain/          ← AgendamentoBuilderTest, HorarioConflictValidatorTest
```

---

## Princípios SOLID Aplicados

| Princípio | Como foi aplicado |
|-----------|-------------------|
| **SRP** | `AgendamentoService` cuida apenas de regras de agendamento. Validação de conflito fica em `HorarioConflictValidator`. |
| **OCP** | Novos tipos de notificação (SMS, push) são adicionados implementando `AgendamentoObserver`, sem modificar o service. |
| **LSP** | `InMemoryAgendamentoRepository` substitui qualquer implementação de `AgendamentoRepository` sem quebrar o sistema. |
| **ISP** | Cada interface tem responsabilidade mínima e coesa (`AgendamentoRepository`, `ConflictValidator`, `AgendamentoObserver`). |
| **DIP** | `AgendamentoService` recebe `AgendamentoRepository` e `ConflictValidator` via construtor — depende de abstrações, não de implementações. |

---

## Padrões de Projeto Utilizados

### Criacional — Builder
**Classe:** `Agendamento.Builder`

`Agendamento` possui vários campos, alguns opcionais (observações, status). O Builder torna a criação legível e evita construtores telescópicos.

```java
Agendamento ag = new Agendamento.Builder()
    .cliente(cliente)
    .servico(servico)
    .dataHora(LocalDateTime.of(2025, 6, 20, 10, 0))
    .observacoes("Cliente prefere tesoura")
    .build();
```

---

### Estrutural — Decorator
**Classe:** `LoggingAgendamentoService`

Adiciona log de auditoria ao `AgendamentoService` sem modificar sua implementação. Basta envolver o serviço base:

```java
var service = new AgendamentoService(repository, validator);
var logged  = new LoggingAgendamentoService(service);
logged.criar(cliente, servico, horario); // registra log automaticamente
```

---

### Comportamental — Observer
**Interface:** `AgendamentoObserver`
**Implementações:** `LogNotificador`, `EmailNotificador`

Ao criar ou cancelar um agendamento, todos os observadores registrados são notificados automaticamente:

```java
service.registrarObserver(new LogNotificador());
service.registrarObserver(new EmailNotificador());
service.criar(cliente, servico, horario); // ambos são notificados
```

---

### Comportamental — Strategy
**Interface:** `ConflictValidator`
**Implementação:** `HorarioConflictValidator`

O algoritmo de detecção de conflito pode ser trocado sem alterar `AgendamentoService`:

```java
// Algoritmo padrão
var service = new AgendamentoService(repository, new HorarioConflictValidator());
```

---

## Diagrama de Classes

![Diagrama de Classes](docs/diagrama-classes.png)

> O diagrama está disponível na pasta `docs/`.

---

## Casos de Teste

| Teste | Descrição |
|-------|-----------|
| `criar_deveRetornarConfirmado_quandoHorarioLivre` | Agendamento sem conflito → status CONFIRMADO |
| `criar_deveLancarConflictException_quandoHorarioOcupado` | Sobreposição de horário → exceção |
| `criar_devePermitir_quandoHorarioIniciaAposTerminoAnterior` | Consecutivos sem gap → permitido |
| `cancelar_deveMudarStatus_quandoAgendamentoExiste` | Cancelamento → status CANCELADO |
| `cancelar_deveLiberarHorario_paraNovoAgendamento` | Após cancelar, horário fica disponível |
| `observer_deveSerNotificado_aoCriarAgendamento` | Criação notifica observer (Mockito) |
| `observer_deveSerNotificado_aoCancelarAgendamento` | Cancelamento notifica observer |
| `builder_deveCriarAgendamento_comTodosOsCamposObrigatorios` | Builder funciona corretamente |
| `temConflito_deveIgnorar_agendamentosCancelados` | Cancelados não geram conflito |

---

## Integrantes do Grupo

| Nome | GitHub |
|------|--------|
| _(preencher)_ | _(preencher)_ |

---

## Evolução do Projeto

O repositório mantém commits incrementais por semana, refletindo as etapas:
1. Definição do problema e requisitos
2. Modelagem e diagrama de classes
3. Implementação das entidades e interfaces
4. Implementação do serviço e padrões
5. Testes unitários
6. Documentação final
