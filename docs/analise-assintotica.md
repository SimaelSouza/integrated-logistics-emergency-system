

## 📊 Tabela Resumida de Complexidade

### Estruturas de Dados (Módulos 1 a 4)

| Módulo / Operação | Melhor Caso | Caso Médio | Pior Caso | Espaço (Auxiliar) |
| :--- | :---: | :---: | :---: | :---: |
| **Módulo 1: Fila de Atendimento Prioritário** | | | | |
| - Inserção (Fila Comum) | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| - Reorganização Dinâmica (Heap/Priority) | $O(1)$ | $O(\log n)$ | $O(\log n)$ | $O(1)$ |
| - Remoção por Atendimento | $O(1)$ ou $O(\log n)$ | $O(1)$ ou $O(\log n)$ | $O(1)$ ou $O(\log n)$ | $O(1)$ |
| - Cálculo de Tempo Médio | $O(n)$ | $O(n)$ | $O(n)$ | $O(1)$ |
| **Módulo 2: Pilha de Operações** | | | | |
| - Empilhamento (Push / Undo) | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| - Desempilhamento (Pop / Redo) | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| **Módulo 3: Lista Encadeada Simples** | | | | |
| - Inserção (Início) | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| - Busca / Atualização | $O(1)$ | $O(n)$ | $O(n)$ | $O(1)$ |
| - Remoção | $O(1)$ | $O(n)$ | $O(n)$ | $O(1)$ |
| **Módulo 4: Lista Duplamente Encadeada** | | | | |
| - Navegação Bidirecional (Avançar/Voltar)| $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| - Remoção Intermediária (com ponteiro) | $O(1)$ | $O(1)$ | $O(1)$ | $O(1)$ |
| - Ordenação Parcial (Insertion Adaptado) | $O(n)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |

### Algoritmos de Ordenação (Módulo 5)

| Algoritmo | Complexidade de Tempo (Melhor) | Complexidade de Tempo (Médio) | Complexidade de Tempo (Pior) | Espaço Auxiliar |
| :--- | :---: | :---: | :---: | :---: |
| **Bubble Sort** | $O(n)$ (otimizado) | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Insertion Sort** | $O(n)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Selection Sort** | $O(n^2)$ | $O(n^2)$ | $O(n^2)$ | $O(1)$ |
| **Merge Sort** | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | $O(n)$ |
| **Quick Sort** | $O(n \log n)$ | $O(n \log n)$ | $O(n^2)$ (pivô ruim) | $O(\log n)$ a $O(n)$ |
| **Counting Sort** | $O(n + k)$ | $O(n + k)$ | $O(n + k)$ | $O(n + k)$ |
| **Radix Sort** | $O(d \cdot (n + k))$ | $O(d \cdot (n + k))$ | $O(d \cdot (n + k))$ | $O(n + k)$ |
| **Bucket Sort** | $O(n + k)$ | $O(n)$ | $O(n^2)$ | $O(n + k)$ |

---

## 📑 Análise Detalhada por Módulo

### 🗂️ Módulo 1 — Fila de Atendimento Prioritário

A eficiência deste módulo depende diretamente da escolha da estrutura subjacente: uma **Fila baseada em Array/Lista Encadeada** ou um **Heap Binário (Fila de Prioridade)**.

1. **Fila Comum (FIFO):**
    - **Inserção e Remoção:** $O(1)$. Inserir no fim e remover do início leva tempo constante se mantidos os ponteiros para `head` (início) e `tail` (fim).
2. **Reorganização Dinâmica e Fila Prioritária:**
    - Se implementada com **Heap Binário Min/Max**, a inserção de um elemento com prioridade leva **$O(\log n)$** no pior caso, pois o elemento pode precisar "subir" (*bubble up*) por toda a altura da árvore. A reorganização dinâmica após alteração de prioridade também consome **$O(\log n)$**.
    - Se implementada sobre uma lista não ordenada, a inserção é $O(1)$, mas a busca pelo elemento de maior prioridade para remoção custará $O(n)$.
3. **Remoção por Atendimento:**
    - Em um Heap, remover o elemento raiz (maior prioridade) custa **$O(\log n)$**, pois o último elemento é movido para a raiz e precisa descer (*bubble down*).
4. **Cálculo de Tempo Médio de Espera e Visualização Completa:**
    - **$O(n)$**: Ambas as operações exigem percorrer linearmente todos os $n$ elementos presentes na fila para somar os tempos ou exibir as informações.

---

### 🔄 Módulo 2 — Pilha de Operações

Uma pilha opera estritamente sob o princípio LIFO (*Last In, First Out*). Pode ser eficientemente implementada usando arrays dinâmicos ou listas encadeadas.

1. **Empilhamento/Desempilhamento (Push/Pop):**
    - **$O(1)$**: Adicionar ou remover do topo da pilha consome tempo constante, independentemente de quantos itens estejam armazenados.
2. **Undo e Redo:**
    - **$O(1)$**: A operação de *Undo* desempilha uma ação da pilha principal e a empilha na pilha de *Redo*. Ambas são operações $O(1)$.
3. **Histórico de Operações:**
    - **$O(n)$**: Para listar todo o histórico para o usuário, é necessário percorrer os elementos da pilha.
4. **Limite de Histórico:**
    - **$O(1)$**: Verificar se a pilha atingiu o limite máximo de tamanho leva tempo constante. Se o limite for atingido e for necessário remover a base (o elemento mais antigo), isso pode custar $O(n)$ em arrays ordinários, ou **$O(1)$** se for utilizada uma lista duplamente encadeada circular ou uma estrutura de buffer circular (*deque*).

---

### 🔗 Módulo 3 — Lista Encadeada Simples

Cada nó armazena o dado e uma referência para o próximo nó. Não há acesso indexado direto.

1. **Inserção:**
    - **$O(1)$** se realizada no início da lista (ou no fim, caso se mantenha um ponteiro para o último nó).
2. **Busca e Atualização:**
    - **Melhor Caso: $O(1)$** (se o elemento for o primeiro).
    - **Caso Médio/Pior Caso: $O(n)$** (se o elemento estiver no meio ou no fim, exigindo uma varredura linear).
3. **Remoção:**
    - **$O(n)$**: Mesmo que desconectar o nó seja $O(1)$, encontrar o nó anterior ao que será removido exige uma busca linear de tempo $O(n)$.
4. **Listagem de Pedidos:**
    - **$O(n)$**: Visitar todos os nós sequencialmente para exibição.

---

### ↕️ Módulo 4 — Lista Duplamente Encadeada

Cada nó possui referências tanto para o próximo nó (`next`) quanto para o nó anterior (`prev`).

1. **Navegação Bidirecional:**
    - **$O(1)$**: Mover para o próximo ou retornar ao anterior a partir do nó atual é imediato devido aos ponteiros bidirecionais.
2. **Remoção Intermediária:**
    - **$O(1)$**: Diferente da lista simples, se já tivermos a referência do nó que deve ser removido, podemos desconectá-lo instantaneamente fazendo `nodo.prev.next = nodo.next` e `nodo.next.prev = nodo.prev`. (Nota: se for necessário *buscar* o nó primeiro, a busca continua sendo $O(n)$).
3. **Histórico Completo:**
    - **$O(n)$**: Percorrer a lista do início ao fim (ou vice-versa).
4. **Ordenação Parcial:**
    - **$O(n^2)$**: Implementada geralmente por meio de algoritmos adaptativos como o *Insertion Sort*, que funciona muito bem para listas quase ordenadas (caso em que atinge **$O(n)$**), mas degrada para quadrático no pior caso.

---

### ⚡ Módulo 5 — Algoritmos de Ordenação

#### Algoritmos Quadráticos

1. **Bubble Sort:**
    - Compara pares adjacentes e os troca se estiverem fora de ordem. Se nenhuma troca ocorrer em uma passagem completa, o algoritmo pode parar (**$O(n)$** no melhor caso). Caso contrário, realiza $n^2$ comparações.
2. **Insertion Sort:**
    - Constrói o array ordenado um elemento por vez. Excelente para dados já ordenados ou quase ordenados (**$O(n)$**), pois faz poucas comparações. No pior caso (dados invertidos), realiza $O(n^2)$ operações.
3. **Selection Sort:**
    - Encontra repetidamente o menor elemento e o coloca na posição correta. Não é adaptativo: realiza sempre o mesmo número de comparações, resultando em **$O(n^2)$** em todos os cenários.

#### Algoritmos Eficientes (Divisão e Conquista)

1. **Merge Sort:**
    - Divide o array ao meio, ordena recursivamente e depois combina (*merge*) as duas metades. A árvore de recursão tem altura $\log n$ e cada nível custa $O(n)$, totalizando de forma consistente **$O(n \log n)$**. Desvantagem: exige espaço auxiliar **$O(n)$** para as cópias dos arrays.
2. **Quick Sort:**
    - Escolhe um pivô, particiona o array ao redor dele e ordena as partições.
    - **Caso Médio/Melhor Caso: $O(n \log n)$** se o pivô dividir o array em partes equilibradas.
    - **Pior Caso: $O(n^2)$** se o pivô for sempre o maior ou menor elemento (ex: array já ordenado com pivô no extremo). O espaço auxiliar varia de $O(\log n)$ (pilha de recursão otimizada) a $O(n)$.

#### Ordenações Lineares (Não baseadas em comparação)

1. **Counting Sort:**
    - Conta as frequências de cada valor único. Funciona em **$O(n + k)$**, onde $k$ é a amplitude dos valores (valor máximo - mínimo). É altamente eficiente se $k$ não for muito maior que $n$. Exige memória adicional significativa $O(n + k)$.
2. **Radix Sort:**
    - Ordena os números dígito por dígito (do menos significativo ao mais significativo) usando um algoritmo estável como base (geralmente Counting Sort). Complexidade: **$O(d \cdot (n + k))$**, onde $d$ é o número de dígitos.
3. **Bucket Sort:**
    - Distribui os elementos em vários "baldes" (*buckets*). Cada balde é ordenado individualmente (geralmente com Insertion Sort). Se os dados forem distribuídos uniformemente, roda em **$O(n)$**. Se todos os elementos caírem no mesmo balde, a complexidade degrada para a do algoritmo de ordenação interna usado (ex: $O(n^2)$).