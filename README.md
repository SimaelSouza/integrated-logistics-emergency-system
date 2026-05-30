# Integrated Logistics Emergency System

Sistema integrado de gerenciamento logístico e atendimento emergencial desenvolvido em Java utilizando estruturas de dados implementadas manualmente.

---

# 📚 Sobre o Projeto

Este projeto foi desenvolvido para a disciplina de Estrutura de Dados com o objetivo de aplicar, na prática, os principais conceitos estudados durante o curso.

O sistema simula operações de gerenciamento logístico e atendimento emergencial, utilizando exclusivamente estruturas de dados implementadas manualmente pelos integrantes.

---

# 🎯 Objetivos do Projeto

- Implementar estruturas de dados sem uso de bibliotecas prontas;
- Desenvolver algoritmos de ordenação manualmente;
- Aplicar conceitos de análise assintótica;
- Construir uma aplicação organizada utilizando arquitetura em camadas;
- Criar uma interface gráfica utilizando JavaFX;
- Trabalhar conceitos de modularização e boas práticas de programação.

---

# 🏗 Arquitetura do Projeto

O sistema utiliza uma arquitetura baseada em:

# MVC + Camada de Estruturas de Dados

```txt
┌─────────────────────┐
│      JavaFX UI      │
│   (Views / FXML)    │
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│     Controllers     │
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│      Services       │
│ (Regras de negócio) │
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│ Estruturas de Dados │
│  Implementação base │
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│     Algoritmos      │
│     Ordenações      │
└─────────────────────┘
```

---

# 📂 Estrutura do Projeto

```txt
integrated-logistics-emergency-system/
│
├── README.md
├── pom.xml
├── .gitignore
│
├── docs/
│   ├── analise-assintotica.md
│   ├── relatorio-final.md
│   └── diagramas/
│
├── src/
│   ├── main/
│   │
│   │   ├── java/
│   │   │
│   │   │   ├── app/
│   │   │   │   └── Main.java
│   │   │   │
│   │   │   ├── controllers/
│   │   │   │   ├── MainController.java
│   │   │   │   ├── AtendimentoController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   ├── HistoricoController.java
│   │   │   │   ├── OrdenacaoController.java
│   │   │   │   └── RelatorioController.java
│   │   │   │
│   │   │   ├── models/
│   │   │   │   ├── Atendimento.java
│   │   │   │   ├── Pedido.java
│   │   │   │   ├── Operacao.java
│   │   │   │   ├── Relatorio.java
│   │   │   │   └── Movimentacao.java
│   │   │   │
│   │   │   ├── services/
│   │   │   │   ├── AtendimentoService.java
│   │   │   │   ├── PedidoService.java
│   │   │   │   ├── HistoricoService.java
│   │   │   │   ├── OrdenacaoService.java
│   │   │   │   ├── RelatorioService.java
│   │   │   │   └── EstatisticaService.java
│   │   │   │
│   │   │   ├── structures/
│   │   │   │
│   │   │   │   ├── queue/
│   │   │   │   │   ├── Fila.java
│   │   │   │   │   ├── FilaPrioritaria.java
│   │   │   │   │   └── NoFila.java
│   │   │   │   │
│   │   │   │   ├── stack/
│   │   │   │   │   ├── Pilha.java
│   │   │   │   │   └── NoPilha.java
│   │   │   │   │
│   │   │   │   ├── singlylinkedlist/
│   │   │   │   │   ├── ListaSimples.java
│   │   │   │   │   └── NoSimples.java
│   │   │   │   │
│   │   │   │   └── doublylinkedlist/
│   │   │   │       ├── ListaDupla.java
│   │   │   │       └── NoDuplo.java
│   │   │   │
│   │   │   ├── algorithms/
│   │   │   │
│   │   │   │   ├── quadratic/
│   │   │   │   │   ├── BubbleSort.java
│   │   │   │   │   ├── InsertionSort.java
│   │   │   │   │   └── SelectionSort.java
│   │   │   │   │
│   │   │   │   ├── efficient/
│   │   │   │   │   ├── MergeSort.java
│   │   │   │   │   └── QuickSort.java
│   │   │   │   │
│   │   │   │   └── linear/
│   │   │   │       ├── CountingSort.java
│   │   │   │       ├── RadixSort.java
│   │   │   │       └── BucketSort.java
│   │   │   │
│   │   │   ├── utils/
│   │   │   │   ├── Timer.java
│   │   │   │   ├── Estatisticas.java
│   │   │   │   ├── Comparador.java
│   │   │   │   └── GeradorDados.java
│   │   │   │
│   │   │   └── exceptions/
│   │   │       ├── EstruturaVaziaException.java
│   │   │       └── PosicaoInvalidaException.java
│   │   │
│   │   └── resources/
│   │
│   │       ├── views/
│   │       │   ├── main-view.fxml
│   │       │   ├── atendimento-view.fxml
│   │       │   ├── pedidos-view.fxml
│   │       │   ├── historico-view.fxml
│   │       │   ├── ordenacao-view.fxml
│   │       │   └── relatorio-view.fxml
│   │       │
│   │       ├── css/
│   │       │   └── style.css
│   │       │
│   │       └── images/
│   │
│   └── test/
│
│       ├── queue/
│       │   ├── FilaTest.java
│       │   └── FilaPrioritariaTest.java
│       │
│       ├── stack/
│       │   └── PilhaTest.java
│       │
│       ├── singlylinkedlist/
│       │   └── ListaSimplesTest.java
│       │
│       ├── doublylinkedlist/
│       │   └── ListaDuplaTest.java
│       │
│       ├── algorithms/
│       │   ├── BubbleSortTest.java
│       │   ├── InsertionSortTest.java
│       │   ├── SelectionSortTest.java
│       │   ├── MergeSortTest.java
│       │   ├── QuickSortTest.java
│       │   ├── CountingSortTest.java
│       │   ├── RadixSortTest.java
│       │   └── BucketSortTest.java
│       │
│       ├── services/
│       │   ├── AtendimentoServiceTest.java
│       │   ├── PedidoServiceTest.java
│       │   ├── HistoricoServiceTest.java
│       │   └── RelatorioServiceTest.java
│       │
│       └── performance/
│           └── BenchmarkTest.java
│
└── target/
```

---

# 🖥 Interface Gráfica

A interface será desenvolvida utilizando:

- JavaFX
- FXML
- CSS

---

# 📌 Funcionalidades

## ✅ Módulo 1 — Fila de Atendimento Prioritário

- Fila comum;
- Fila prioritária;
- Reorganização dinâmica;
- Remoção por atendimento;
- Cálculo de tempo médio de espera;
- Visualização completa da fila.

---

## ✅ Módulo 2 — Pilha de Operações

- Undo;
- Redo;
- Histórico de operações;
- Empilhamento/desempilhamento;
- Limite de histórico.

---

## ✅ Módulo 3 — Lista Encadeada Simples

- Inserção;
- Remoção;
- Busca;
- Atualização;
- Listagem de pedidos.

---

## ✅ Módulo 4 — Lista Duplamente Encadeada

- Navegação bidirecional;
- Remoção intermediária;
- Histórico completo;
- Ordenação parcial.

---

## ✅ Módulo 5 — Algoritmos de Ordenação

### Algoritmos Quadráticos

- Bubble Sort
- Insertion Sort
- Selection Sort

### Algoritmos Eficientes

- Merge Sort
- Quick Sort

### Ordenações Lineares

- Counting Sort
- Radix Sort
- Bucket Sort

---

## ✅ Módulo 6 — Relatórios Estatísticos

- Tempo médio de execução;
- Quantidade de operações;
- Comparações;
- Trocas;
- Desempenho das estruturas;
- Análise de crescimento.

---

# 🧠 Estruturas de Dados Implementadas

Todas as estruturas serão implementadas manualmente.

## Estruturas:

- Filas
- Filas Prioritárias
- Pilhas
- Lista Encadeada Simples
- Lista Duplamente Encadeada

---

# ⚠ Restrições do Projeto

Não será permitido utilizar:

- ArrayList
- LinkedList
- Queue
- Stack
- Deque
- Bibliotecas externas de estruturas de dados

---

# ⚙ Tecnologias Utilizadas

- Java 17+
- JavaFX
- Maven
- JUnit 5

---

# 🧪 Testes

O projeto contará com testes para:

- Estruturas de dados;
- Algoritmos;
- Services;
- Desempenho;
- Análise assintótica.

---

# 📈 Organização das Camadas

## `views/`

Responsável por:

- Telas;
- Botões;
- Inputs;
- Tabelas;
- Interface visual.

---

## `controllers/`

Responsável por:

- Capturar eventos;
- Chamar services;
- Atualizar interface.

---

## `services/`

Responsável pelas regras de negócio:

- Gerenciamento de filas;
- Controle de pedidos;
- Relatórios;
- Histórico;
- Estatísticas.

---

## `structures/`

Núcleo principal do projeto:

- Filas;
- Pilhas;
- Listas;
- Nós;
- Manipulação manual.

---

## `algorithms/`

Responsável pelos algoritmos de ordenação e análise de desempenho.

---

# 🔄 Fluxo da Aplicação

```txt
Usuário
   ↓
JavaFX UI
   ↓
Controllers
   ↓
Services
   ↓
Estruturas de Dados
   ↓
Algoritmos
```

---

# 👥 Divisão da Dupla

## Integrante 1 — Backend Estrutural

Responsável por:

- Filas;
- Pilhas;
- Services;
- Lógica;
- Relatórios.

---

## Integrante 2 — Interface e Ordenações

Responsável por:

- JavaFX;
- Controllers;
- Telas;
- Algoritmos;
- Análise assintótica.

---

# 📊 Exemplo de Interface

## Tela Inicial

```txt
+------------------------------------------------+
| SISTEMA LOGÍSTICO EMERGENCIAL                  |
+------------------------------------------------+
| [ Atendimento ]                                |
| [ Pedidos ]                                    |
| [ Histórico ]                                  |
| [ Ordenações ]                                 |
| [ Relatórios ]                                 |
| [ Estatísticas ]                               |
+------------------------------------------------+
```

---

# 📌 Exemplo de Visualização da Fila

```txt
┌───────────────────────────────────┐
│ PRIORIDADE │ NOME │ HORÁRIO       │
├───────────────────────────────────┤
│ 5           João     14:22        │
│ 4           Maria    14:25        │
│ 2           Carlos   14:30        │
└───────────────────────────────────┘
```

---

# 🚀 Como Executar

## Clonar o repositório

```bash
git clone https://github.com/seu-usuario/integrated-logistics-emergency-system.git
```

---

## Entrar na pasta

```bash
cd integrated-logistics-emergency-system
```

---

## Executar o projeto

```bash
mvn clean javafx:run
```

---

# 📖 Boas Práticas Utilizadas

- Separação em camadas;
- Orientação a objetos;
- Modularização;
- Estruturas genéricas;
- Responsabilidade única;
- Arquitetura limpa;
- Organização profissional.

---

# 🏆 Objetivo Acadêmico

Este projeto busca demonstrar domínio em:

- Estrutura de Dados;
- Algoritmos;
- Análise Assintótica;
- Arquitetura de Software;
- Programação Orientada a Objetos;
- Desenvolvimento Desktop com JavaFX.

---

# 👨‍💻 Integrantes

- Simael Da Silva Souza
- Arione Maia Gomes Filho

---

# 📄 Licença

Projeto desenvolvido exclusivamente para fins acadêmicos.