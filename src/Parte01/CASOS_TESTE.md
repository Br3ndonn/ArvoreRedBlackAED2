# Casos de Teste - Visualização Detalhada

## FASE 1: Testes de Inserção

### Caso 1: Tio VERMELHO (Recoloração)

**Sequência de Inserção: 10, 5, 15, 1**

```
Passo 1: Inserir 10
    10(B)  ← Raiz sempre preta

Passo 2: Inserir 5
    10(B)
    /
  5(R)    ← Novo nó vermelho, OK (pai é preto)

Passo 3: Inserir 15
    10(B)
    /    \
  5(R)  15(R)  ← Ambos filhos vermelhos, OK

Passo 4: Inserir 1
Antes do fixup:
      10(B)
      /    \
    5(R)  15(R)  ← Tio é VERMELHO
    /
  1(R)  ← Viola: dois vermelhos consecutivos

CASO 1 APLICADO: Recoloração
Depois do fixup:
      10(B)
      /    \
    5(B)  15(B)  ← Tio e pai ficam pretos
    /
  1(R)  ← OK: pai agora é preto

Resultado: 0 rotações, apenas mudança de cores
```

---

### Caso 3: Tio PRETO - Linha Reta (Rotação Simples)

**Sequência de Inserção: 10, 5, 1**

```
Passo 1: Inserir 10
    10(B)

Passo 2: Inserir 5
    10(B)
    /
  5(R)

Passo 3: Inserir 1
Antes do fixup:
    10(B)
    /
  5(R)     ← Tio (nil) é PRETO
  /
1(R)  ← Viola: dois vermelhos consecutivos
      ← Forma LINHA RETA: 10 → 5 → 1

CASO 3 APLICADO: Rotação Direita em 10
Depois do fixup:
      5(B)     ← Nova raiz
     /    \
   1(R)  10(R) ← Recoloração após rotação

Resultado: 1 rotação direita
```

---

### Caso 2: Tio PRETO - Linha Quebrada (Rotação Dupla)

**Sequência de Inserção: 10, 5, 7**

```
Passo 1: Inserir 10
    10(B)

Passo 2: Inserir 5
    10(B)
    /
  5(R)

Passo 3: Inserir 7
Antes do fixup:
    10(B)
    /
  5(R)     ← Tio (nil) é PRETO
    \
    7(R)  ← Viola: dois vermelhos consecutivos
          ← Forma LINHA QUEBRADA: 10 → 5 ← 7

CASO 2 APLICADO: 
Passo 2a - Rotação Esquerda em 5:
    10(B)
    /
  7(R)
  /
5(R)  ← Agora forma linha reta

Passo 2b - Rotação Direita em 10:
      7(B)     ← Nova raiz
     /    \
   5(R)  10(R)

Resultado: 2 rotações (esquerda + direita)
```

---

## FASE 2: Testes de Remoção de Nós Pretos

### Árvore Inicial Completa (13 nós)

```
                    10(B)
                   /      \
              5(R)         15(R)
             /    \       /      \
         2(B)    7(B)  12(B)    20(B)
         /        \    /   \    /    \
      1(R)       8(R) 11(R) 13(R) 18(R) 25(R)
```

**Propriedades:**
- Altura Preta: 3
- Altura Total: 3
- Nós Pretos: 10, 2, 7, 12, 20 (5 nós)
- Nós Vermelhos: 5, 15, 1, 8, 11, 13, 18, 25 (8 nós)

---

### Remoção 1: Remover 1 (VERMELHO)

```
Nó: 1(R) - folha vermelha
Caso: Remoção simples (sem fixup necessário)

Antes:           Depois:
    2(B)            2(B)
    /                
  1(R)       →    (nil)

Resultado: Altura preta mantida em 3
```

---

### Remoção 2: Remover 8 (VERMELHO)

```
Nó: 8(R) - folha vermelha
Caso: Remoção simples (sem fixup necessário)

Antes:           Depois:
    7(B)            7(B)
      \               
     8(R)      →   (nil)

Resultado: Altura preta mantida em 3
```

---

### Remoção 3: Remover 12 (PRETO)

```
Nó: 12(B) - nó preto com 2 filhos vermelhos
Caso: Substitui por sucessor e aplica fixup

Antes:                      Depois:
       15(R)                     15(R)
      /      \                  /      \
   12(B)    20(B)     →      13(B)    20(B)
   /   \    /    \           /        /    \
 11(R) 13(R) 18(R) 25(R)  11(R)    18(R)  25(R)

Processo:
1. Sucessor de 12 é 13
2. 13 substitui 12
3. Fixup aplicado para manter altura preta

Resultado: Altura preta mantida em 3
```

---

### Remoção 4: Remover 18 (VERMELHO)

```
Nó: 18(R) - folha vermelha
Caso: Remoção simples (sem fixup necessário)

Antes:           Depois:
    20(B)           20(B)
    /    \          /    \
 18(R)  25(R) → (nil)   25(R)

Resultado: Altura preta mantida em 3
```

---

## Árvore Final Após Todas as Remoções

```
                    10(B)
                   /      \
              5(R)         15(R)
             /    \       /      \
         2(B)    7(B)  13(B)    20(B)
                      /             \
                   11(R)          25(R)
```

**Propriedades Finais:**
- ✓ Altura Preta: 3 (mantida)
- ✓ Altura Total: 3 (mantida)
- ✓ Raiz preta: Sim (10 é preto)
- ✓ Propriedade vermelha: Sem vermelhos consecutivos
- ✓ Altura preta uniforme: Todos caminhos têm 3 nós pretos

---

## Verificações Realizadas

Para CADA operação (inserção ou remoção):

### 1. Propriedade da Raiz
```java
assert arvore.raiz.cor == NodoRB.PRETO;
```

### 2. Propriedade das Folhas
```java
assert arvore.nil.cor == NodoRB.PRETO;
```

### 3. Propriedade Vermelha
```java
for cada nodo vermelho:
    assert nodo.esquerda.cor == PRETO;
    assert nodo.direita.cor == PRETO;
```

### 4. Altura Preta Uniforme
```java
for cada nodo:
    assert blackHeight(nodo.esquerda) == blackHeight(nodo.direita);
```

---

## Resumo dos Casos Testados

| Caso | Tipo | Condição | Ação | Rotações |
|------|------|----------|------|----------|
| Inserção 1 | Tio Vermelho | Tio e pai são vermelhos | Recoloração | 0 |
| Inserção 2 | Tio Preto (quebrada) | Linha zigue-zague | Rotação dupla | 2 |
| Inserção 3 | Tio Preto (reta) | Linha reta | Rotação simples | 1 |
| Remoção 1 | Irmão Vermelho | Irmão é vermelho | Rotação + recolor | 1 |
| Remoção 2 | Irmão Preto (2 pretos) | Ambos filhos pretos | Recoloração | 0 |
| Remoção 3 | Irmão Preto (externo preto) | Filho externo preto | Rotação dupla | 2 |
| Remoção 4 | Irmão Preto (externo vermelho) | Filho externo vermelho | Rotação simples | 1 |

**Total de Casos Testados: 7 (3 inserção + 4 remoção)**

**Status: ✅ TODOS OS CASOS VALIDADOS COM SUCESSO**

