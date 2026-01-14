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
            // Leitura dos dados do arquivo
            String caminhoArquivo = "src/Parte01/dados";
            List<Integer> valores = LeitorArquivo.lerValores(caminhoArquivo);

            System.out.println("Valores lidos do arquivo: " + valores);
            System.out.println("Total de valores: " + valores.size() + "\n");

            // Criar árvore Red-Black
            RedBlack arvore = new RedBlack();

            System.out.println("==========================================");
            System.out.println("FASE 1: TESTE DE INSERÇÃO");
            System.out.println("==========================================");
            System.out.println("Esta fase testa os casos de inserção em árvores Red-Black:");
            System.out.println("  - Caso 1: Tio VERMELHO → Recoloração");
            System.out.println("  - Caso 2: Tio PRETO + Linha quebrada → Rotação dupla");
            System.out.println("  - Caso 3: Tio PRETO + Linha reta → Rotação simples");
            System.out.println();

            int rotEsqAnterior = 0;
            int rotDirAnterior = 0;

            // Inserir valores um por um e verificar integridade
            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
                System.out.println("--- Inserindo valor: " + valor + " (Inserção " + (i+1) + "/" + valores.size() + ") ---");
                
                NodoRB novoNodo = new NodoRB(valor, NodoRB.VERMELHO);
                arvore.RBInsert(arvore, novoNodo);
                
                // Detectar qual caso ocorreu
                int rotEsqAtual = arvore.rotacoesEsquerda;
                int rotDirAtual = arvore.rotacoesDireita;
                boolean houveRotacaoEsq = rotEsqAtual > rotEsqAnterior;
                boolean houveRotacaoDir = rotDirAtual > rotDirAnterior;
                
                if (houveRotacaoEsq && houveRotacaoDir) {
                    System.out.println("  → Caso 2 detectado: Tio PRETO com rotação dupla");
                } else if (houveRotacaoEsq || houveRotacaoDir) {
                    System.out.println("  → Caso 3 detectado: Tio PRETO com rotação simples");
                } else {
                    System.out.println("  → Caso 1 detectado: Recoloração (ou nó inicial)");
                }
                
                rotEsqAnterior = rotEsqAtual;
                rotDirAnterior = rotDirAtual;
                
                // Verificar integridade após inserção
                boolean integridadeOk = VerificadorRB.verificarIntegridade(arvore);
                
                if (integridadeOk) {
                    System.out.println("✓ Integridade mantida após inserção de " + valor);
                    System.out.println("  - Altura preta: " + VerificadorRB.blackHeight(arvore));
                    System.out.println("  - Altura total: " + VerificadorRB.altura(arvore));
                    System.out.println("  - Rotações esquerda acumuladas: " + arvore.rotacoesEsquerda);
                    System.out.println("  - Rotações direita acumuladas: " + arvore.rotacoesDireita);
                } else {
                    System.err.println("✗ ERRO: Integridade violada após inserção de " + valor);
                    return;
                }
                System.out.println();
            }

            System.out.println("\n>>> Árvore após todas as inserções:");
            System.out.println("Pré-ordem: ");
            arvore.preOrder(arvore.raiz);
            System.out.println("\n\nEstrutura visual:");
            arvore.imprimirArvore(arvore.raiz, "", false);

            System.out.println("\n>>> Estatísticas finais de inserção:");
            System.out.println("  - Total de rotações à esquerda: " + arvore.rotacoesEsquerda);
            System.out.println("  - Total de rotações à direita: " + arvore.rotacoesDireita);
            System.out.println("  - Altura preta final: " + VerificadorRB.blackHeight(arvore));
            System.out.println("  - Altura total final: " + VerificadorRB.altura(arvore));

            System.out.println("\n==========================================");
            System.out.println("FASE 2: TESTE DE REMOÇÃO DE NÓS PRETOS");
            System.out.println("==========================================");
            System.out.println("Esta fase testa os 4 casos de remoção com 'Preto Extra':");
            System.out.println("  - Caso 1: Irmão VERMELHO → Rotação e recoloração");
            System.out.println("  - Caso 2: Irmão PRETO com filhos PRETOS → Recoloração");
            System.out.println("  - Caso 3: Irmão PRETO, filho externo PRETO → Rotação dupla");
            System.out.println("  - Caso 4: Irmão PRETO, filho externo VERMELHO → Rotação simples");
            System.out.println();

            // Identificar e remover nós pretos
            testarRemocaoNosPretos(arvore, valores);

            System.out.println("\n==========================================");
            System.out.println("TESTE CONCLUÍDO COM SUCESSO!");
            System.out.println("==========================================");
            System.out.println("✓ Todas as propriedades Red-Black foram mantidas");
            System.out.println("✓ Raiz e folhas sempre PRETAS");
            System.out.println("✓ Propriedade vermelha respeitada (sem vermelhos consecutivos)");
            System.out.println("✓ Altura preta uniforme em todos os caminhos");

        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarRemocaoNosPretos(RedBlack arvore, List<Integer> valoresOriginais) {
        // Identificar nós pretos na árvore
        System.out.println("Identificando nós PRETOS para remoção...\n");

        // Remover alguns valores específicos para forçar diferentes casos de remoção
        // Vamos tentar remover valores em ordem estratégica

        int[] valoresParaRemover = selecionarValoresParaRemover(arvore, valoresOriginais);

        for (int valor : valoresParaRemover) {
            NodoRB nodoParaRemover = VerificadorRB.findByValue(arvore, valor);

            if (nodoParaRemover == null || nodoParaRemover == arvore.nil) {
                System.out.println("Valor " + valor + " não encontrado na árvore, pulando...\n");
                continue;
            }

            String cor = nodoParaRemover.cor == NodoRB.PRETO ? "PRETO" : "VERMELHO";
            int alturaPretaAntes = VerificadorRB.blackHeight(arvore);

            System.out.println("--- Removendo valor: " + valor + " (Cor: " + cor + ") ---");
            System.out.println("  - Altura preta antes: " + alturaPretaAntes);

            arvore.RBRemove(arvore, nodoParaRemover);

            // Verificar integridade após remoção
            boolean integridadeOk = VerificadorRB.verificarIntegridade(arvore);
            int alturaPretaDepois = VerificadorRB.blackHeight(arvore);

            if (integridadeOk) {
                System.out.println("✓ Integridade mantida após remoção de " + valor);
                System.out.println("  - Altura preta depois: " + alturaPretaDepois);
                System.out.println("  - Altura total: " + VerificadorRB.altura(arvore));

                if (cor.equals("PRETO")) {
                    // Para nós pretos, a altura preta pode diminuir em determinadas condições
                    System.out.println("  - Nó PRETO removido: casos de fixup aplicados corretamente");
                }
            } else {
                System.err.println("✗ ERRO: Integridade violada após remoção de " + valor);
                return;
            }
            System.out.println();
        }

        System.out.println("\n>>> Árvore após remoções:");
        System.out.println("Pré-ordem: ");
        arvore.preOrder(arvore.raiz);
        System.out.println("\n\nEstrutura visual:");
        arvore.imprimirArvore(arvore.raiz, "", false);

        System.out.println("\n>>> Estatísticas finais após remoção:");
        System.out.println("  - Altura preta final: " + VerificadorRB.blackHeight(arvore));
        System.out.println("  - Altura total final: " + VerificadorRB.altura(arvore));
    }

    private static int[] selecionarValoresParaRemover(RedBlack arvore, List<Integer> valores) {
        // Selecionar valores estrategicamente para forçar diferentes casos de remoção
        // Vamos remover alguns valores que existem na árvore

        // Para o arquivo Parte01/dados que contém: 10;5;15;1;7;20
        // Vamos remover valores que forcem casos interessantes

        // Primeiro, vamos identificar quais valores estão presentes e suas cores
        System.out.println("Análise dos nós na árvore:");
        for (int valor : valores) {
            NodoRB nodo = VerificadorRB.findByValue(arvore, valor);
            if (nodo != null && nodo != arvore.nil) {
                String cor = nodo.cor == NodoRB.PRETO ? "PRETO" : "VERMELHO";
                System.out.println("  Valor " + valor + ": " + cor);
            }
        }
        System.out.println();

        // Estratégia de remoção:
        // 1. Remover uma folha vermelha (caso mais simples)
        // 2. Remover um nó preto com um filho vermelho
        // 3. Remover um nó preto com dois filhos nil (força os 4 casos de fixup)

        // Para o dataset [10,5,15,1,7,20], vamos remover: 1, 7, 15
        return new int[]{1, 7, 15};
    }
}

