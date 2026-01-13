package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivo {
    public static List<Integer> lerValores(String caminhoArquivo) throws IOException {
        List<Integer> valores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                adicionarValoresDaLinha(linha, valores);
            }
        }
        return valores;
    }

    private static void adicionarValoresDaLinha(String linha, List<Integer> valores) {
        String[] partes = linha.split(";");
        for (String parte : partes) {
            try {
                int valor = Integer.parseInt(parte.trim());
                valores.add(valor);
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido ignorado: " + parte);
            }
        }
    }
}