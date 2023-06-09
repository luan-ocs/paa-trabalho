package main.java.org.luaneric;

import main.java.org.luaneric.backpack.BackpackProblemSolve;
import main.java.org.luaneric.binaryThree.CustomBinaryNode;
import main.java.org.luaneric.binaryThree.CustomBinaryTree;

import java.io.*;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private static final Clock clock = Clock.systemUTC();
    static String filepath100k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_100k.txt";
    static String filepath200k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_200k.txt";
    static String filepath300k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_300k.txt";
    static String filepath400k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_400k.txt";
    static String filepath500k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_500k.txt";
    static String filepath600k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_600k.txt";
    static String filepath700k = "/home/abas/Desktop/personal/faculdade/paa/algorithms/src/main/resources/palavras_700k.txt";

    public static int compareByWord(CustomBinaryNode a, CustomBinaryNode b) {
        return a.getNodeValue().getTreeCustomValue().toString()
                .compareTo(b.getNodeValue().getTreeCustomValue().toString());
    }

    public static void writeStringToFile(String content, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static CustomBinaryTree extractWordsFromFile(String filePath) throws IOException {

        CustomBinaryTree words = new CustomBinaryTree((c1, c2) -> compareByWord((CustomBinaryNode) c1, (CustomBinaryNode) c2));

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split(" ");
                for (String word : lineWords) {
                    if (!word.isEmpty()) {
                        GenericCustomValue<String> wordToTree= new GenericCustomValue(word);
                        words.insert(wordToTree);
                    }
                }
            }
        }

        return words;
    }



    public static void main(String[] args) throws IOException {

        String[] filepaths = {
                filepath100k,
                filepath200k,
                filepath300k,
                filepath400k,
                filepath500k,
                filepath600k,
                filepath700k
        };

        for (String filepath : filepaths) {

             System.out.println("Lendo arquivo " +  getSizefromPath(filepath) + "\n\n");

             Long[] times = generateOrderDocument(filepath, getSizefromPath(filepath), "/home/abas/Desktop/paa");

             System.out.println("Relatorio das medias: ");

             System.out.println("Alfabeticamente (com leitura de arquivo): " + times[0]);
             System.out.println("Reordenando para frequencia: "+ times[1]);

             System.out.println("------------------------------------------------");
        }

        System.out.println("\n\n\n Problema da mochila: \n");

        CustomBinaryTree tree = extractWordsFromFile(filepath100k);

        tree.setFunctionCompare((f1, f2) -> f2.getFrequency() - f1.getFrequency());

        BackpackProblemSolve solver = new BackpackProblemSolve();

        solver.execute(tree.toArray());



    }

    public static String getSizefromPath(String path) {
        Pattern pattern = Pattern.compile("\\d+k");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        return matcher.group();
    }



    public static Long[] generateOrderDocument(String filepath, String size, String outputBasepath) throws IOException {

            Long averageAlf = 0L;
            Long averageFreq = 0L;

            for(int i = 0; i < 3; i++) {

                Instant initAlf = clock.instant();

                CustomBinaryTree tree = extractWordsFromFile(filepath);
                String ordered = tree.printInOrder();

                Instant endAlf = clock.instant();

                Long execTimeAlf = endAlf.toEpochMilli() - initAlf.toEpochMilli();

                System.out.println("Tempo de execução " + execTimeAlf + " Milissegundos  - tamanho: " + size + " Lexicograficamente");

                averageAlf += execTimeAlf;

                writeStringToFile(ordered, outputBasepath + "/ordered" + size + ".txt");

                Instant initFreq = clock.instant();

                tree.setFunctionCompare((c1, c2) -> c2.getFrequency() - c1.getFrequency());
                String orderedByFreq = tree.printInOrder();

                Instant endFreq = clock.instant();

                Long execTimeFreq = endFreq.toEpochMilli() - initFreq.toEpochMilli();

                System.out.println("Tempo de execução " + execTimeFreq + " Milissegundos  - tamanho: " + size + " Frequencia");

                averageFreq += execTimeFreq;
                writeStringToFile(orderedByFreq, outputBasepath + "/frequency" + size + ".txt");


            }

            Long finalTimeFreq = averageFreq / 3;
            Long finalTimeAlf = averageAlf / 3;

            Long[] times = {finalTimeAlf, finalTimeFreq};
            return times;
    }



}