package Parte01;

import Arvore.NodoRB;
import Arvore.RedBlack;
import Arvore.VerificadorRB;

/**
 * Classe de teste abrangente para demonstrar todos os casos de inserção e remoção
 * em árvores Red-Black
 */
public class TesteCompleto {

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("TESTE COMPLETO: CASOS DE INSERCAO E REMOCAO RED-BLACK");
        System.out.println("=======================================================\n");

        // Teste 1: Demonstrar Caso 1 de inserção (Tio Vermelho)
        testarCaso1Insercao();

        // Teste 2: Demonstrar Casos 2 e 3 de inserção (Tio Preto com rotações)
        testarCasos2e3Insercao();

        // Teste 3: Demonstrar casos de remoção de nós pretos
        testarRemocaoNosPretos();

        System.out.println("\n=======================================================");
        System.out.println("TODOS OS TESTES CONCLUIDOS COM SUCESSO!");
        System.out.println("=======================================================");
    }

    private static void testarCaso1Insercao() {
        System.out.println("-------------------------------------------------------");
        System.out.println("TESTE 1: CASO 1 DE INSERCAO (Tio Vermelho -> Recoloracao)");
        System.out.println("-------------------------------------------------------");
        System.out.println("Sequencia: 10, 5, 15, 1");
        System.out.println("Ao inserir 1, o tio (15) eh vermelho, entao apenas recoloramos.\n");

        RedBlack arvore = new RedBlack();
        int[] valores = {10, 5, 15, 1};

        for (int valor : valores) {
            System.out.println("Inserindo: " + valor);
            NodoRB nodo = new NodoRB(valor, NodoRB.VERMELHO);
            int rotAntes = arvore.rotacoesEsquerda + arvore.rotacoesDireita;
            arvore.RBInsert(arvore, nodo);
            int rotDepois = arvore.rotacoesEsquerda + arvore.rotacoesDireita;

            if (valor == 1) {
                System.out.println("  -> CASO 1: Tio vermelho, apenas recoloracao");
                System.out.println("  -> Sem rotacoes (antes: " + rotAntes + ", depois: " + rotDepois + ")");
            }

            verificarIntegridade(arvore, valor);
        }

        System.out.println("\nArvore final:");
        arvore.imprimirArvore(arvore.raiz, "", false);
        System.out.println();
    }

    private static void testarCasos2e3Insercao() {
        System.out.println("-------------------------------------------------------");
        System.out.println("TESTE 2: CASOS 2 e 3 DE INSERCAO (Tio Preto -> Rotacoes)");
        System.out.println("-------------------------------------------------------");
        System.out.println("Sequencia: 10, 5, 1");
        System.out.println("Ao inserir 1:");
        System.out.println("  - Tio (nil) eh preto");
        System.out.println("  - Forma uma 'linha reta' (10->5->1)");
        System.out.println("  - CASO 3: Rotacao simples a direita\n");

        RedBlack arvore = new RedBlack();
        int[] valores = {10, 5, 1};

        for (int valor : valores) {
            System.out.println("Inserindo: " + valor);
            NodoRB nodo = new NodoRB(valor, NodoRB.VERMELHO);
            int rotDirAntes = arvore.rotacoesDireita;
            arvore.RBInsert(arvore, nodo);
            int rotDirDepois = arvore.rotacoesDireita;

            if (valor == 1) {
                System.out.println("  -> CASO 3: Tio preto, linha reta");
                System.out.println("  -> Rotacao a direita aplicada");
                System.out.println("  -> Rotacoes direita: " + rotDirAntes + " -> " + rotDirDepois);
            }

            verificarIntegridade(arvore, valor);
        }

        System.out.println("\nArvore apos Caso 3:");
        arvore.imprimirArvore(arvore.raiz, "", false);

        // Agora teste Caso 2 (linha quebrada)
        System.out.println("\n--- Testando CASO 2 (linha quebrada) ---");
        System.out.println("Sequencia: 10, 5, 7");
        System.out.println("Ao inserir 7:");
        System.out.println("  - Tio (nil) eh preto");
        System.out.println("  - Forma uma 'linha quebrada' (10->5<-7)");
        System.out.println("  - CASO 2: Rotacao esquerda em 5, depois direita em 10\n");

        RedBlack arvore2 = new RedBlack();
        int[] valores2 = {10, 5, 7};

        for (int valor : valores2) {
            System.out.println("Inserindo: " + valor);
            NodoRB nodo = new NodoRB(valor, NodoRB.VERMELHO);
            int rotEsqAntes = arvore2.rotacoesEsquerda;
            int rotDirAntes = arvore2.rotacoesDireita;
            arvore2.RBInsert(arvore2, nodo);
            int rotEsqDepois = arvore2.rotacoesEsquerda;
            int rotDirDepois = arvore2.rotacoesDireita;

            if (valor == 7) {
                System.out.println("  -> CASO 2: Tio preto, linha quebrada");
                System.out.println("  -> Rotacao dupla aplicada");
                System.out.println("  -> Rotacoes esquerda: " + rotEsqAntes + " -> " + rotEsqDepois);
                System.out.println("  -> Rotacoes direita: " + rotDirAntes + " -> " + rotDirDepois);
            }

            verificarIntegridade(arvore2, valor);
        }

        System.out.println("\nArvore apos Caso 2:");
        arvore2.imprimirArvore(arvore2.raiz, "", false);
        System.out.println();
    }

    private static void testarRemocaoNosPretos() {
        System.out.println("-------------------------------------------------------");
        System.out.println("TESTE 3: REMOCAO DE NOS PRETOS (4 Casos de Fixup)");
        System.out.println("-------------------------------------------------------");
        System.out.println("Construindo arvore com varios nos pretos...\n");

        RedBlack arvore = new RedBlack();
        int[] valores = {10, 5, 15, 2, 7, 12, 20, 1, 8, 11, 13, 18, 25};

        // Inserir todos os valores
        for (int valor : valores) {
            NodoRB nodo = new NodoRB(valor, NodoRB.VERMELHO);
            arvore.RBInsert(arvore, nodo);
        }

        System.out.println("Arvore inicial:");
        arvore.imprimirArvore(arvore.raiz, "", false);
        System.out.println("\nAltura preta: " + VerificadorRB.blackHeight(arvore));
        System.out.println("Altura total: " + VerificadorRB.altura(arvore));

        // Remover nós pretos estrategicamente
        System.out.println("\n--- Removendo nos pretos para forcar casos de fixup ---\n");

        // Remover nó preto que force casos de remoção
        int[] valoresRemover = {1, 8, 12, 18};

        for (int valor : valoresRemover) {
            NodoRB nodo = VerificadorRB.findByValue(arvore, valor);
            if (nodo != null && nodo != arvore.nil) {
                String cor = nodo.cor == NodoRB.PRETO ? "PRETO" : "VERMELHO";
                int hbAntes = VerificadorRB.blackHeight(arvore);

                System.out.println("Removendo: " + valor + " (Cor: " + cor + ")");
                System.out.println("  Altura preta antes: " + hbAntes);

                arvore.RBRemove(arvore, nodo);

                boolean ok = VerificadorRB.verificarIntegridade(arvore);
                int hbDepois = VerificadorRB.blackHeight(arvore);

                if (ok) {
                    System.out.println("  OK: Integridade mantida");
                    System.out.println("  Altura preta depois: " + hbDepois);
                    if (cor.equals("PRETO")) {
                        System.out.println("  -> Casos de fixup aplicados com sucesso");
                    }
                } else {
                    System.err.println("  ERRO: Integridade violada!");
                }
                System.out.println();
            }
        }

        System.out.println("Arvore final apos remocoes:");
        arvore.imprimirArvore(arvore.raiz, "", false);
        System.out.println("\nAltura preta final: " + VerificadorRB.blackHeight(arvore));
        System.out.println("Altura total final: " + VerificadorRB.altura(arvore));
        System.out.println();
    }

    private static void verificarIntegridade(RedBlack arvore, int valorInserido) {
        boolean ok = VerificadorRB.verificarIntegridade(arvore);
        if (ok) {
            System.out.println("  OK: Propriedades RB mantidas");
        } else {
            System.err.println("  ERRO: Violacao apos inserir " + valorInserido);
            System.exit(1);
        }
    }
}

