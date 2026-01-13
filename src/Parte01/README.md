# Parte 01: Auditoria e Garantia de Propriedades (Corretude)

## Descrição

Este módulo implementa testes abrangentes para validar a corretude da implementação de árvore Red-Black, verificando todas as propriedades fundamentais após operações de inserção e remoção.

## Implementação

### Verificador de Integridade (VerificadorRB.java)

O módulo `VerificadorRB` já estava implementado e garante os seguintes pontos:

1. **Raiz e Folhas**: Verifica que a raiz é PRETA e todas as folhas (T.nil) são PRETAS
2. **Propriedade Vermelha**: Assegura que não há dois nós vermelhos consecutivos
3. **Altura Preta (Black-Height)**: Valida que todos os caminhos da raiz às folhas contêm o mesmo número de nós pretos

### Classes de Teste Criadas

#### 1. Main.java (src/Parte01/Main.java)

Classe principal que:
- Lê valores do arquivo `src/Parte01/dados`
- Testa inserções incrementais verificando integridade após cada operação
- Detecta automaticamente qual caso de inserção ocorreu:
  - **Caso 1**: Tio VERMELHO → Apenas recoloração
  - **Caso 2**: Tio PRETO + Linha quebrada → Rotação dupla
  - **Caso 3**: Tio PRETO + Linha reta → Rotação simples
- Testa remoções de nós pretos forçando os 4 casos de fixup
- Exibe estatísticas detalhadas e visualização da árvore

#### 2. TesteCompleto.java (src/Parte01/TesteCompleto.java)

Classe de teste mais abrangente que demonstra explicitamente:

**Testes de Inserção:**
- **Caso 1**: Sequência [10, 5, 15, 1] - Demonstra recoloração quando tio é vermelho
- **Caso 3**: Sequência [10, 5, 1] - Demonstra rotação simples (linha reta)
- **Caso 2**: Sequência [10, 5, 7] - Demonstra rotação dupla (linha quebrada)

**Testes de Remoção:**
- Constrói árvore maior com 13 nós
- Remove nós pretos estrategicamente
- Valida que os 4 casos de fixup de remoção são aplicados corretamente:
  - **Caso 1**: Irmão VERMELHO → Rotação e recoloração
  - **Caso 2**: Irmão PRETO com filhos PRETOS → Recoloração
  - **Caso 3**: Irmão PRETO, filho externo PRETO → Rotação dupla
  - **Caso 4**: Irmão PRETO, filho externo VERMELHO → Rotação simples

## Arquivos de Dados

### dados
Contém valores básicos para teste: `10;5;15;1;7;20`

### dados_completo
Contém valores mais abrangentes: `10;5;15;1;7;12;20;0;3;8;11;13;18;25`

## Como Executar

### Compilar os arquivos:
```bash
cd D:\Programacao\java\ArvoreRedBlack
javac -d out -sourcepath src src/Parte01/Main.java
javac -d out -sourcepath src src/Parte01/TesteCompleto.java
```

### Executar teste básico (lê arquivo dados):
```bash
java -cp out Parte01.Main
```

### Executar teste completo (demonstra todos os casos):
```bash
java -cp out Parte01.TesteCompleto
```

## Saída Esperada

Os testes validam e exibem:

1. ✓ **Status de Integridade**: Confirma que todas as propriedades RB são mantidas
2. **Altura Preta**: Número de nós pretos em qualquer caminho raiz→folha
3. **Altura Total**: Profundidade máxima da árvore
4. **Rotações**: Contadores de rotações à esquerda e à direita
5. **Visualização da Árvore**: Representação hierárquica com indicação de cores (R=Red, B=Black)
6. **Detecção de Casos**: Identifica automaticamente qual caso de inserção/remoção foi aplicado

## Propriedades Verificadas

### 1. Raiz Preta
- Verificado em `VerificadorRB.verificarIntegridade()`
- A raiz sempre deve ser preta após qualquer operação

### 2. Folhas Pretas
- Todas as folhas (nós nil) são pretas por definição
- Verificado no construtor de RedBlack

### 3. Propriedade Vermelha
- Implementado em `verificarPropriedadeVermelho()`
- Garante que nenhum nó vermelho tem filho vermelho
- Percorre toda a árvore recursivamente

### 4. Altura Preta Uniforme
- Implementado em `verificaAlturaNegra()`
- Calcula altura preta de cada subárvore
- Garante que todas têm a mesma altura preta
- Retorna -1 em caso de violação

## Casos de Teste Demonstrados

### Inserção - Caso 1 (Tio Vermelho)
```
Antes:       10(B)          Depois:      10(B)
            /     \                     /     \
          5(R)   15(R)                5(B)   15(B)
                                      /
Inserir: 1(R) →                    1(R)
```
Apenas recoloração, sem rotações.

### Inserção - Caso 3 (Tio Preto, Linha Reta)
```
Antes:    10(B)           Depois:     5(B)
          /                          /    \
        5(R)          →           1(R)   10(R)
        /
      1(R)
```
Rotação simples à direita em 10.

### Inserção - Caso 2 (Tio Preto, Linha Quebrada)
```
Antes:    10(B)           Intermediário:  10(B)      Depois:    7(B)
          /                               /                     /    \
        5(R)            →              7(R)          →       5(R)   10(R)
          \                            /
          7(R)                       5(R)
```
Rotação esquerda em 5, depois direita em 10.

### Remoção de Nós Pretos
Os testes removem nós pretos que forçam a aplicação dos 4 casos de fixup:
- Remoção de folhas pretas
- Remoção de nós pretos com um filho
- Remoção de nós pretos com dois filhos
- Validação de que a altura preta permanece uniforme

## Conclusão

A implementação demonstra que:
- ✓ Todas as propriedades Red-Black são mantidas após inserções
- ✓ Todas as propriedades Red-Black são mantidas após remoções
- ✓ Os 3 casos de inserção são tratados corretamente
- ✓ Os 4 casos de remoção são tratados corretamente
- ✓ A altura preta permanece uniforme em todos os caminhos
- ✓ Não há violações da propriedade vermelha (nós vermelhos consecutivos)
- ✓ A raiz é sempre preta

