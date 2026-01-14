package Parte02;

import Arvore.NodoRB;
import Arvore.RedBlack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FaseB {

    public static void main(String[] args) {
        System.out.println("  SISTEMA DE PROCESSAMENTO DE DADOS - RED-BLACK TREE");
        System.out.println("=============================================================\n");

        try {
            System.out.println("FASE B: CONSULTA (LEITURA INTENSIVA)");
            System.out.println("=============================================================");
            System.out.println("Ação: Realizar 200.000 buscas na Red-Black Tree");
            System.out.println("  - 100.000 chaves existentes (200.000 a 900.000)");
            System.out.println("  - 100.000 chaves não existentes (0 a 200.000)");
            System.out.println();
            System.out.println("ANÁLISE TEÓRICA:");
            System.out.println("  - Altura Red-Black: h ≤ 2×log₂(n+1)");
            System.out.println("  - Altura AVL: h ≤ 1,44×log₂(n+2)");
            System.out.println("  - AVL mais balanceada → buscas potencialmente mais rápidas");
            System.out.println();

            RedBlack arvore = new RedBlack();
            Random random = new Random(42); // seed fixo para reprodutibilidade
            List<Integer> dadosConsulta = new ArrayList<>();

            System.out.println("-------------------------------------------------------------");
            System.out.println("PASSO 1: Gerando e inserindo 100.000 inteiros (200.000-900.000)");
            System.out.println("-------------------------------------------------------------");

            List<Integer> chavesExistentes = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                int valor = 200000 + random.nextInt(700001); // 200.000 a 900.000
                chavesExistentes.add(valor);
                dadosConsulta.add(valor);

                NodoRB novoNodo = new NodoRB(valor, NodoRB.VERMELHO);
                arvore.RBInsert(arvore, novoNodo);
            }

            System.out.println("100.000 inteiros inseridos na árvore");
            System.out.println();

            // Passo 2: Gerar 100.000 inteiros entre 0 e 200.000 (não inserir)
            System.out.println("-------------------------------------------------------------");
            System.out.println("PASSO 2: Gerando 100.000 inteiros não existentes (0-200.000)");
            System.out.println("-------------------------------------------------------------");

            List<Integer> chavesNaoExistentes = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                int valor = random.nextInt(200001); // 0 a 200.000
                chavesNaoExistentes.add(valor);
                dadosConsulta.add(valor);
            }

            System.out.println("✓ 100.000 inteiros não existentes gerados");
            System.out.println();

            // Passo 3: Salvar dados de consulta no arquivo
            System.out.println("-------------------------------------------------------------");
            System.out.println("PASSO 3: Salvando dados de consulta");
            System.out.println("-------------------------------------------------------------");

            String caminhoConsulta = "src/Parte01/dadosConsulta.txt";
            salvarDadosConsulta(caminhoConsulta, dadosConsulta);

            System.out.println("Arquivo salvo: " + caminhoConsulta);
            System.out.println("  Total de valores: " + dadosConsulta.size());
            System.out.println();

            // Passo 4: Ler arquivo e realizar buscas
            System.out.println("=============================================================");
            System.out.println("REALIZANDO BUSCAS");
            System.out.println("=============================================================");

            List<Integer> valoresParaBuscar = lerDadosConsulta(caminhoConsulta);
            System.out.println("Total de valores a buscar: " + valoresParaBuscar.size());
            System.out.println();

            int encontrados = 0;
            int naoEncontrados = 0;

            System.out.println("Iniciando buscas...");
            System.out.println("-------------------------------------------------------------");

            // Medir tempo APENAS das buscas
            long tempoInicio = System.nanoTime();

            for (int i = 0; i < valoresParaBuscar.size(); i++) {
                int valor = valoresParaBuscar.get(i);
                NodoRB resultado = arvore.buscarNodo(arvore, valor);

                if (resultado != null && resultado != arvore.nil) {
                    encontrados++;
                } else {
                    naoEncontrados++;
                }
            }

            long tempoFim = System.nanoTime();
            long tempoTotalNano = tempoFim - tempoInicio;
            double tempoTotalMs = tempoTotalNano / 1_000_000.0;

            System.out.println("Buscas concluídas!");
            System.out.println();

            // Passo 5: Calcular altura da árvore
            int alturaArvore = calcularAltura(arvore.raiz, arvore.nil);

            // Exibir resultados
            System.out.println("RESULTADOS DA FASE B:");
            System.out.println("-------------------------------------------------------------");
            System.out.println("Tempo Total de Busca:");
            System.out.println("  " + String.format(java.util.Locale.US, "%.6f", tempoTotalMs) + " milissegundos");
            System.out.println("  " + String.format(java.util.Locale.US, "%.6f", tempoTotalMs / 1000.0) + " segundos");
            System.out.println();
            System.out.println("Resultados das Buscas:");
            System.out.println("  Total de buscas: " + valoresParaBuscar.size());
            System.out.println("  Inteiros encontrados: " + encontrados);
            System.out.println("  Inteiros não encontrados: " + naoEncontrados);
            System.out.println();
            System.out.println("Estrutura da Árvore:");
            System.out.println("  Altura máxima: " + alturaArvore);
            System.out.println("  Altura máxima teórica: " + (int)(2 * Math.log(100000 + 1) / Math.log(2)));
            System.out.println();
            // Salvar resultados no arquivo CSV
            String caminhoSaida = "src/Resultado/resultadoTempoBuscaRedBlack.csv";
            salvarResultados(caminhoSaida, tempoTotalMs, encontrados, naoEncontrados);

            System.out.println();
            System.out.println("Resultados salvos em: " + caminhoSaida);
            System.out.println();
        } catch (IOException e) {
            System.err.println("ERRO ao processar arquivos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Salva os dados de consulta no arquivo com separador ;
     */
    private static void salvarDadosConsulta(String caminhoArquivo, List<Integer> valores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, false))) {
            for (int i = 0; i < valores.size(); i++) {
                writer.write(String.valueOf(valores.get(i)));
                if (i < valores.size() - 1) {
                    writer.write(";");
                }
            }
        }
    }

    /**
     * Lê os dados de consulta do arquivo
     */
    private static List<Integer> lerDadosConsulta(String caminhoArquivo) throws IOException {
        String conteudo = new String(Files.readAllBytes(Paths.get(caminhoArquivo)));
        String[] partes = conteudo.split(";");

        List<Integer> valores = new ArrayList<>();
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                valores.add(Integer.parseInt(parte.trim()));
            }
        }

        return valores;
    }

    /**
     * Calcula a altura da árvore
     */
    private static int calcularAltura(NodoRB nodo, NodoRB nil) {
        if (nodo == nil || nodo == null) {
            return 0;
        }

        int alturaEsquerda = calcularAltura(nodo.esquerda, nil);
        int alturaDireita = calcularAltura(nodo.direita, nil);

        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    /**
     * Salva os resultados no arquivo CSV
     */
    private static void salvarResultados(String caminhoArquivo, double tempoMs, int encontrados, int naoEncontrados) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, false))) {
            // Escrever cabeçalho
            writer.write("tempoBuscaMs,inteirosEncontrados,inteirosNaoEncontrados");
            writer.newLine();

            // Escrever dados (usar Locale.US para garantir ponto decimal)
            String tempoFormatado = String.format(java.util.Locale.US, "%.6f", tempoMs);
            writer.write(tempoFormatado + "," + encontrados + "," + naoEncontrados);
            writer.newLine();
        }
    }
}

