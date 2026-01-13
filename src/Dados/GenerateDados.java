package Dados;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateDados {
    public static void main(String[] args) {
        int count = 1000000;
        int min = 0;
        int max = 1000000; // inclusive
        Random rand = new Random();
        File out = new File("src/Dados/dados");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out, false))) {
            for (int i = 0; i < count; i++) {
                int val = rand.nextInt(max - min + 1) + min;
                // write value and a separator; after every 5th value write a newline
                int posInLine = i % 5;
                if (posInLine == 0) {
                    // first value in the line: write value
                    bw.write(Integer.toString(val));
                } else {
                    // subsequent values: prefix with semicolon
                    bw.write(";" + val);
                }

                if (posInLine == 4 || i == count - 1) {
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever arquivo: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Wrote " + count + " random integers to " + out.getPath());
    }
}
