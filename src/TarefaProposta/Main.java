package TarefaProposta;

import Arvore.NodoRB;
import Arvore.RedBlack;

public class Main {
    public static void main(String[] args) {
        System.out.println("  DEMONSTRAÇÃO DE ÁRVORE RED-BLACK");

        RedBlack arvore = new RedBlack();

        int[] chavesInserir = {41, 38, 31, 12, 19, 8};

        System.out.println("FASE 1: INSERÇÕES SUCESSIVAS");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Inserindo as chaves: 41, 38, 31, 12, 19, 8\n");

        for (int chave : chavesInserir) {
            System.out.println("Inserindo " + chave);
            NodoRB novoNodo = new NodoRB(chave, NodoRB.VERMELHO);
            arvore.RBInsert(arvore, novoNodo);

            System.out.print(" Árvore: ");
            arvore.preOrder(arvore.raiz);
            System.out.println("\n");
        }

        System.out.println("\nÁRVORE FINAL APÓS TODAS AS INSERÇÕES:");
        System.out.println("Representação em pré-ordem: ");
        arvore.preOrder(arvore.raiz);

        System.out.println("\n\n=============================================================");
        System.out.println("FASE 2: REMOÇÕES SUCESSIVAS");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Removendo as chaves na ordem: 8, 12, 19, 31, 38, 41\n");

        int[] chavesRemover = {8, 12, 19, 31, 38, 41};

        for (int chave : chavesRemover) {
            System.out.println(">>> Removendo chave: " + chave);

            NodoRB nodoRemover = arvore.buscarNodo(arvore, chave);

            if (nodoRemover != null) {
                arvore.RBRemove(arvore, nodoRemover);

                if (arvore.raiz == arvore.nil) {
                    System.out.println("Árvore: [VAZIA]\n");
                } else {
                    System.out.print("Árvore: ");
                    arvore.preOrder(arvore.raiz);
                    System.out.println("\n");
                }
            } else {
                System.out.println("ERRO: Chave " + chave + " não encontrada!\n");
            }
        }

        System.out.println("ÁRVORE FINAL APÓS TODAS AS REMOÇÕES:");
        if (arvore.raiz == arvore.nil) {
            System.out.println("VAZIA - Todas as chaves removidas!");
        } else {
            System.out.println("Representação em pré-ordem: ");
            arvore.preOrder(arvore.raiz);
        }
    }
}
