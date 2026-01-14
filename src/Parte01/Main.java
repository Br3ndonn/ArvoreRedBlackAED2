package Parte01;

import Arvore.NodoRB;
import Arvore.RedBlack;
import Arvore.VerificadorRB;
import Util.LeitorArquivo;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Parte 01: Auditoria e Garantia de Propriedades (Corretude) ===\n");

        try {
            String caminhoArquivo = "src/Parte01/dados";
            List<Integer> valores = LeitorArquivo.lerValores(caminhoArquivo);

            System.out.println("Valores lidos do arquivo: " + valores);
            System.out.println("Total de valores: " + valores.size() + "\n");

            RedBlack arvore = new RedBlack();

            System.out.println("FASE 1: TESTE DE INSERÇÃO");
            System.out.println("==========================================");
            System.out.println("Teste de casos de inserção em árvores Red-Black:");
            System.out.println("  - Caso 1: Tio VERMELHO → Recoloração");
            System.out.println("  - Caso 2: Tio PRETO + Linha quebrada → Rotação dupla");
            System.out.println("  - Caso 3: Tio PRETO + Linha reta → Rotação simples");
            System.out.println();

            int rotEsqAnterior = 0;
            int rotDirAnterior = 0;

            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
                System.out.println(" Inserindo: " + valor);
                
                NodoRB novoNodo = new NodoRB(valor, NodoRB.VERMELHO);
                arvore.RBInsert(arvore, novoNodo);
                
                // Detectar qual caso ocorreu
                int rotEsqAtual = arvore.rotacoesEsquerda;
                int rotDirAtual = arvore.rotacoesDireita;
                boolean houveRotacaoEsq = rotEsqAtual > rotEsqAnterior;
                boolean houveRotacaoDir = rotDirAtual > rotDirAnterior;
                
                if (houveRotacaoEsq && houveRotacaoDir) {
                    System.out.println("  Caso 2 detectado: Tio PRETO com rotação dupla");
                } else if (houveRotacaoEsq || houveRotacaoDir) {
                    System.out.println("  Caso 3 detectado: Tio PRETO com rotação simples");
                } else {
                    System.out.println("  Caso 1 detectado: Recoloração (ou nó inicial)");
                }
                
                rotEsqAnterior = rotEsqAtual;
                rotDirAnterior = rotDirAtual;
                
                // Verificar integridade após inserção
                boolean integridadeOk = VerificadorRB.verificarIntegridade(arvore);
                
                if (integridadeOk) {
                    System.out.println("Integridade mantida após inserção de " + valor);
                    System.out.println("  - Altura preta: " + VerificadorRB.blackHeight(arvore));
                    System.out.println("  - Altura total: " + VerificadorRB.altura(arvore));
                    System.out.println("  - Rotações esquerda acumuladas: " + arvore.rotacoesEsquerda);
                    System.out.println("  - Rotações direita acumuladas: " + arvore.rotacoesDireita);
                } else {
                    System.err.println("ERRO: Integridade violada após inserção de " + valor);
                    return;
                }
                System.out.println();
            }

            System.out.println("\nÁrvore após todas as inserções:");
            System.out.println("Pré-ordem: ");
            arvore.preOrder(arvore.raiz);

            System.out.println("\nEstatísticas finais de inserção:");
            System.out.println("  - Total de rotações à esquerda: " + arvore.rotacoesEsquerda);
            System.out.println("  - Total de rotações à direita: " + arvore.rotacoesDireita);
            System.out.println("  - Altura preta final: " + VerificadorRB.blackHeight(arvore));
            System.out.println("  - Altura total final: " + VerificadorRB.altura(arvore));

            System.out.println("\n==========================================");
            System.out.println("TESTE CONCLUÍDO COM SUCESSO!");
            System.out.println("==========================================");
            System.out.println("Todas as propriedades Red-Black foram mantidas");
            System.out.println("Raiz e folhas sempre PRETAS");
            System.out.println("Propriedade vermelha respeitada (sem vermelhos consecutivos)");
            System.out.println("Altura preta uniforme em todos os caminhos");

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

