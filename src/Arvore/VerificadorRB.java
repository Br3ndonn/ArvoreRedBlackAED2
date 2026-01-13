package Arvore;

public class VerificadorRB {
    public static boolean verificarIntegridade(RedBlack T) {
        StringBuilder erros = new StringBuilder();

        if (T == null) {
            System.err.println("Arvore nula");
            return false;
        }

        if (T.nil == null) {
            erros.append("T.nil é null\n");
        } else if (T.nil.cor != NodoRB.PRETO) {
            erros.append("T.nil não é preto\n");
        }

        if (T.raiz == null) {
            erros.append("T.raiz é null\n");
        } else if (T.raiz != T.nil && T.raiz.cor != NodoRB.PRETO) {
            erros.append("T.raiz não é preto\n");
        }

        boolean redOk = verificarPropriedadeVermelho(T, T.raiz, erros);

        int hb = verificaAlturaNegra(T, T.raiz, erros);

        boolean ok = erros.length() == 0 && redOk && hb >= 0;
        if (!ok) {
            System.err.println("Erros encontrados na verificação da árvore RB:\n" + erros.toString());
        }
        return ok;
    }

    private static boolean verificarPropriedadeVermelho(RedBlack T, NodoRB nodo, StringBuilder erros) {
        if (nodo == null || nodo == T.nil) {
            return true;
        }
        if (nodo.cor == NodoRB.VERMELHO) {
            // se o nó é vermelho, ambos os filhos não podem ser vermelhos
            if (nodo.esquerda != null && nodo.esquerda != T.nil && nodo.esquerda.cor == NodoRB.VERMELHO) {
                erros.append("Dois vermelhos consecutivos: pai=").append(nodo.valor).append(" filhoEsq=").append(nodo.esquerda.valor).append("\n");
                return false;
            }
            if (nodo.direita != null && nodo.direita != T.nil && nodo.direita.cor == NodoRB.VERMELHO) {
                erros.append("Dois vermelhos consecutivos: pai=").append(nodo.valor).append(" filhoDir=").append(nodo.direita.valor).append("\n");
                return false;
            }
        }
        return verificarPropriedadeVermelho(T, nodo.esquerda, erros) && verificarPropriedadeVermelho(T, nodo.direita, erros);
    }

    private static int verificaAlturaNegra(RedBlack T, NodoRB nodo, StringBuilder erros) {
        if(nodo == null) {
            erros.append("Encontrado nodo null\n");
            return -1;
        }
        if (nodo == T.nil) {
            return 1;
        }
        int alturaEsq = verificaAlturaNegra(T, nodo.esquerda, erros);
        int alturaDir = verificaAlturaNegra(T, nodo.direita, erros);
        if(alturaEsq == -1 || alturaDir == -1) {
            return -1;
        }
        if (alturaEsq != alturaDir) {
            erros.append("Alturas negras diferentes no nodo ").append(nodo.valor).append(": esquerda=").append(alturaEsq).append(", direita=").append(alturaDir).append("\n");
            return -1;
        }
        int add = (nodo.cor == NodoRB.PRETO) ? 1 : 0;
        return alturaEsq + add;
    }

    // utilitário para buscar nó por valor (retorna null se não encontrado)
    public static NodoRB findByValue(RedBlack T, int valor) {
        if (T == null) return null;
        NodoRB cur = T.raiz;
        while (cur != null && cur != T.nil) {
            if (valor == cur.valor) return cur;
            cur = (valor < cur.valor) ? cur.esquerda : cur.direita;
        }
        return null;
    }

    // retorna a black-height da raiz (ou -1 em caso de violação)
    public static int blackHeight(RedBlack T) {
        if (T == null) return -1;
        StringBuilder sb = new StringBuilder();
        int hb = verificaAlturaNegra(T, T.raiz, sb);
        return hb;
    }

    // retorna a altura total da árvore (número de arestas do caminho mais longo da raiz até uma folha)
    public static int altura(RedBlack T) {
        if (T == null || T.raiz == null || T.raiz == T.nil) return 0;
        return calcularAltura(T, T.raiz);
    }

    private static int calcularAltura(RedBlack T, NodoRB nodo) {
        if (nodo == null || nodo == T.nil) return -1;
        int alturaEsq = calcularAltura(T, nodo.esquerda);
        int alturaDir = calcularAltura(T, nodo.direita);
        return 1 + Math.max(alturaEsq, alturaDir);
    }
}
