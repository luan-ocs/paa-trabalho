package main.java.org.luaneric;

import main.java.org.luaneric.backpack.BackpackProblemSolve;
import main.java.org.luaneric.binaryThree.CustomBinaryNode;
import main.java.org.luaneric.binaryThree.CustomBinaryTree;

import java.io.*;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final Clock clock = Clock.systemUTC();

    static String outputPath = Paths.get("out").toString();

    static String filepath100k = Paths.get("src/main/resources/palavras_100k.txt").toString();
    static String filepath200k = Paths.get("src/main/resources/palavras_200k.txt").toString();
    static String filepath300k = Paths.get("src/main/resources/palavras_300k.txt").toString();
    static String filepath400k = Paths.get("src/main/resources/palavras_400k.txt").toString();
    static String filepath500k = Paths.get("src/main/resources/palavras_500k.txt").toString();
    static String filepath600k = Paths.get("src/main/resources/palavras_600k.txt").toString();
    static String filepath700k = Paths.get("src/main/resources/palavras_700k.txt").toString();
    static int quant = 3;

    static String[] filepaths = {
            filepath100k,
            filepath200k,
            filepath300k,
            filepath400k,
            filepath500k,
            filepath600k,
            filepath700k
    };

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
    public static void appendLineToFile(String filePath, String lineToAdd) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        writer.write(lineToAdd);
        writer.newLine();
        writer.close();
    }

    public static CustomBinaryTree extractWordsFromFile(String filePath) throws IOException {
        CustomBinaryTree words = new CustomBinaryTree(
                (c1, c2) -> compareByWord((CustomBinaryNode) c1, (CustomBinaryNode) c2));

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split(" ");
                for (String word : lineWords) {
                    if (!word.isEmpty()) {
                        GenericCustomValue wordToTree = new GenericCustomValue(word);
                        words.insert(wordToTree);
                    }
                }
            }
        }
        return words;
    }

    public static String getSizefromPath(String path) {
        Pattern pattern = Pattern.compile("\\d+k");
        Matcher matcher = pattern.matcher(path);
        matcher.find();
        return matcher.group();
    }

    public static void removeFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static Long[] generateOrderDocument(String filepath, String size, String outputBasepath) throws IOException {
        Long averageAlf = 0L;
        Long averageFreq = 0L;

        ArrayList<Long> timesAlf = new ArrayList<>();
        ArrayList<Long> timesFreq = new ArrayList<>();

        String alftime = "/alftime.txt";
        String freqtime = "/freqtime.txt";

        for (int i = 0; i < quant; i++) {
            Instant initAlf = clock.instant();

            CustomBinaryTree tree = extractWordsFromFile(filepath);
            String ordered = tree.printInOrder();

            Instant endAlf = clock.instant();

            Long execTimeAlf = endAlf.toEpochMilli() - initAlf.toEpochMilli();
            timesAlf.add(execTimeAlf);

            System.out.println(
                    "Tempo de execução " + execTimeAlf + " Milissegundos  - tamanho: " + size + " Lexicograficamente");

            averageAlf += execTimeAlf;

            writeStringToFile(ordered, outputBasepath + "/ordered" + size + ".txt");

            Instant initFreq = clock.instant();

            tree.setFunctionCompare((c1, c2) -> {

                int frequencyComparision = Integer.compare(c1.getFrequency(), c2.getFrequency());

                if(frequencyComparision != 0) {
                    return frequencyComparision;
                } else {
                    return compareByWord(c1, c2);
                }
            });
            String orderedByFreq = tree.printInOrder();

            Instant endFreq = clock.instant();


            Long execTimeFreq = endFreq.toEpochMilli() - initFreq.toEpochMilli();
            timesFreq.add(execTimeFreq);



            System.out.println(
                    "Tempo de execução " + execTimeFreq + " Milissegundos  - tamanho: " + size + " Frequencia");

            averageFreq += execTimeFreq;
            writeStringToFile(orderedByFreq, outputBasepath + "/frequency" + size + ".txt");
        }

        appendLineToFile(outputBasepath + alftime, timesAlf.toString().replaceAll("\\[|\\]", ""));
        appendLineToFile(outputBasepath + freqtime, timesFreq.toString().replaceAll("\\[|\\]", ""));
        
        Long finalTimeFreq = averageFreq / quant;
        Long finalTimeAlf = averageAlf / quant;

        Long[] times = { finalTimeAlf, finalTimeFreq };
        return times;
    }

    public static void renderReport(String method, Long[] times) {
        System.out.println("Metodo: " + method);
        System.out.println("Relatório das médias: ");
        System.out.println("Alfabeticamente (com leitura de arquivo): "+ times[0]);
        System.out.println("Reordenando por frequencia: " + times[1]);
        System.out.println("--------------------------------------------------------------------");
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        String alftime = "/alftime.txt";
        String freqtime = "/freqtime.txt";

        removeFileIfExists(outputPath  + alftime);
        removeFileIfExists(outputPath + freqtime);

        for (String filepath : filepaths) {

            System.out.println("\nLendo arquivo " + getSizefromPath(filepath) + ":");

            Long[] times = generateOrderDocument(filepath, getSizefromPath(filepath),
                    outputPath);

           renderReport("Arvore avl com redução de problema", times);
        }

        System.out.println("\nProblema da mochila:");

        CustomBinaryTree tree = extractWordsFromFile(filepath100k);

        tree.setFunctionCompare((f1, f2) -> f2.getFrequency() - f1.getFrequency());

        BackpackProblemSolve solver = new BackpackProblemSolve();

        solver.execute(tree.toArray());
    }
}