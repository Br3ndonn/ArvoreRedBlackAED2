package Parte01;

import Arvore.NodoRB;
import Arvore.RedBlack;

public class TesteRemoveNoPreto {

    public static void main(String[] args) {
        System.out.println("  TESTE: REMOÇÃO DE NÓ PRETO");
        System.out.println("=============================================================\n");


        RedBlack arvore = new RedBlack();

        int[] chavesInserir = {50, 25, 75, 10, 30, 60, 80};

        System.out.println("FASE 1: CONSTRUÇÃO DA ÁRVORE");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Inserindo as chaves: 50, 25, 75, 10, 30, 60, 80\n");

        for (int chave : chavesInserir) {
            System.out.println("Inserindo chave: " + chave);
            NodoRB novoNodo = new NodoRB(chave, NodoRB.VERMELHO);
            arvore.RBInsert(arvore, novoNodo);

            System.out.print("Árvore: ");
            arvore.preOrder(arvore.raiz);
            System.out.println("\n");
        }

        System.out.println("\nÁRVORE COMPLETA APÓS INSERÇÕES:");
        System.out.println("Representação em pré-ordem:");
        arvore.preOrder(arvore.raiz);

        System.out.println("\n\n=============================================================");
        System.out.println("FASE 2: REMOÇÃO DE NÓ PRETO");
        System.out.println("-------------------------------------------------------------");

        // Identificar e remover um nó preto
        System.out.println("Identificando nós pretos na árvore...\n");

        int chaveRemover = arvore.raiz.valor;
        NodoRB nodoRemover = arvore.buscarNodo(arvore, chaveRemover);

        if (nodoRemover != null) {
            String cor = (nodoRemover.cor == NodoRB.PRETO) ? "PRETO" : "VERMELHO";
            System.out.println("Removendo chave: " + chaveRemover + " (cor: " + cor + ")");

            arvore.RBRemove(arvore, nodoRemover);

            System.out.print("Árvore após remoção: ");
            arvore.preOrder(arvore.raiz);
        }

        System.out.println("\n\n=============================================================");
        System.out.println("  TESTE CONCLUÍDO");
    }
}

