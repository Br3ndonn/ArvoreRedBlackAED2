package Parte02;

import Arvore.NodoRB;
import Arvore.RedBlack;
import Util.LeitorArquivo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FaseAIngestao {

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("  SISTEMA DE PROCESSAMENTO DE DADOS - RED-BLACK TREE");
        System.out.println("=============================================================\n");

        try {
            String caminhoArquivo = "src/Dados/dados";

            List<Integer> valores = LeitorArquivo.lerValores(caminhoArquivo);
            System.out.println("Total de valores lidos: " + valores.size());
            System.out.println();

            System.out.println("FASE A: INGESTÃO (ESCRITA INTENSIVA)");
            System.out.println("=============================================================");
            System.out.println("Ação: Inserir " + valores.size() + " inteiros na Árvore RedBlack ");
            System.out.println();
            RedBlack arvore = new RedBlack();

            long tempoInicio = System.nanoTime();

            for (int valor : valores) {
                NodoRB novoNodo = new NodoRB(valor, NodoRB.VERMELHO);
                arvore.RBInsert(arvore, novoNodo);
            }

            long tempoFim = System.nanoTime();
            long tempoTotalNano = tempoFim - tempoInicio;
            double tempoTotalMs = tempoTotalNano / 1_000_000.0;

            int rotacoesEsquerda = arvore.rotacoesEsquerda;
            int rotacoesDireita = arvore.rotacoesDireita;
            int rotacoesTotal = rotacoesEsquerda + rotacoesDireita;

            System.out.println("Inserções concluídas!");
            System.out.println();
            System.out.println("RESULTADOS DA FASE A:");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Tempo Total de Inserção:");
            System.out.println("  " + String.format("%.6f", tempoTotalMs) + " milissegundos");
            System.out.println("  " + String.format("%.6f", tempoTotalMs / 1000.0) + " segundos");
            System.out.println();
            System.out.println("Número de Rotações:");
            System.out.println("  Total: " + rotacoesTotal);
            System.out.println("  Rotações à Esquerda: " + rotacoesEsquerda);
            System.out.println("  Rotações à Direita: " + rotacoesDireita);
            System.out.println();
            // Salvar resultados no arquivo CSV
            String caminhoSaida = "src/Resultado/resultadoTempoInsercaoRedBlack.csv";
            salvarResultados(caminhoSaida, tempoTotalMs, rotacoesTotal, rotacoesEsquerda, rotacoesDireita);

            System.out.println();
            System.out.println("Resultados salvos em: " + caminhoSaida);
            System.out.println();
            System.out.println("=============================================================");
            System.out.println("  FASE A CONCLUÍDA COM SUCESSO!");

        } catch (IOException e) {
            System.err.println("ERRO ao processar arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void salvarResultados(String caminhoArquivo, double tempoTotalMs, int rotacoesTotal,
                                         int rotacoesEsquerda, int rotacoesDireita) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, false))) {
            writer.write("tempoTotalInsercao(ms),rotacoesTotal,rotacaoEsquerda,rotacaoDireita");
            writer.newLine();

            String tempoFormatado = String.format(java.util.Locale.US, "%.6f", tempoTotalMs);
            writer.write(tempoFormatado + "," + rotacoesTotal + "," + rotacoesEsquerda + "," + rotacoesDireita);
            writer.newLine();
        }
    }
}

