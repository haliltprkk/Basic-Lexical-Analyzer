/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author haliltprkk
 */
public class LexAnalyzer {
    public static ArrayList<String> arrayListControlStatements = new ArrayList<>();
    public static ArrayList<String> arrayListVariableTypes = new ArrayList<>();
    public static ArrayList<String> arrayListOperators = new ArrayList<>();
    public static ArrayList<String> arrayListPunctations = new ArrayList<>();
    static String[] parts;
    static String[] inputArray;
    static int reservedWordsNumber = 0;


    public static void readControlStatements() throws IOException {
        File f = new File("C:\\Users\\haliltprkk\\IdeaProjects\\LexAnalyzer\\src\\controlStatements.txt");
        if (!f.exists()) {
            System.out.println("File couldn't find");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        System.out.println("-------------------");
        while ((line = br.readLine()) != null) {
            arrayListControlStatements.add(line);
        }
    }

    public static void readVariableTypes() throws IOException {
        File f = new File("C:\\Users\\haliltprkk\\IdeaProjects\\LexAnalyzer\\src\\variableTypes.txt");
        if (!f.exists()) {
            System.out.println("File couldn't find");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        System.out.println("-------------------");
        while ((line = br.readLine()) != null) {
            arrayListVariableTypes.add(line);
        }
    }

    public static void readPunctuations() throws IOException {
        File f = new File("C:\\Users\\haliltprkk\\IdeaProjects\\LexAnalyzer\\src\\punctuations.txt");
        if (!f.exists()) {
            System.out.println("File couldn't find");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        System.out.println("-------------------");
        while ((line = br.readLine()) != null) {
            arrayListPunctations.add(line);
        }
    }

    public static void readOperators() throws IOException {
        File f = new File("C:\\Users\\haliltprkk\\IdeaProjects\\LexAnalyzer\\src\\operators.txt");
        if (!f.exists()) {
            System.out.println("File couldn't find");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        System.out.println("-------------------");
        while ((line = br.readLine()) != null) {
            arrayListOperators.add(line);
        }
    }

    public static void main(String[] args) throws IOException {
        readControlStatements();
        Scanner input = new Scanner(System.in);
        System.out.println("Type the code :");
        String data = input.nextLine();
        compareTheInput(data);

    }

    public static void compareTheInput(String data) {
        inputArray = splitTheInputWithSpace(data);
        int arrayMaxSize = inputArray.length - 1;
        int firstletterValue = inputArray[0].charAt(0);
        if ((firstletterValue >= 49) == true && (firstletterValue <= 57) == true) {  //ilk karakterin sayı olıp olmadığını kontrol ediyorum.
            System.out.println("first character can't be numeric!!");
        }
        boolean isIF = false;
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < arrayListControlStatements.size(); j++) {
                parts = arrayListControlStatements.get(j).split("-");  //burda dosyadan okuduğum veriyi kullanıcının girdiği verilerle karşılaştırıyorum
                if (inputArray[i].equals(parts[0])) {
                    if (inputArray[0].equals("if")) {
                        isIF = true;
                        ifControl();
                        break;
                    }
                    if (inputArray[0].equals("else") && !inputArray[1].equals("if")) {
                        isIF = true;
                        elseControl();
                        break;
                    }
                    if (inputArray[0].equals("switch")) {
                        isIF = true;
                        switchControl();
                        break;
                    }
                    if (inputArray[0].equals("else") && inputArray[1].equals("if")) {
                        isIF = true;
                        elseIfControl();
                        break;
                    }
                    // System.out.println(parts[0] + " --------" + parts[1]);
                    //reservedWordsNumber++;
                    /*if (inputArray[arrayMaxSize].endsWith(";")) {
                        System.out.println(parts[0] + " --------" + "; noktali virgül");
                    } else {
                        System.out.println("Syntax error");
                    }*/
                }
            }
            if (isIF) break;
        }
        System.out.println(inputArray.length - reservedWordsNumber + " adet kullanici tanimli ifade vardir.");
        System.out.println("-------------------");

    }

    public static String[] splitTheInputWithSpace(String data) {
        String inputArray[] = data.trim().split(" ");  //burda kullanıcının girdiği metni boşluklara göre ayırıp bir dizide tutuyorum.
        return inputArray;
    }

    public static void elseControl() {
        int flag = 0;
        int arrayMaxSize = inputArray.length;
        boolean cntl1 = false, cntl2 = false;
        System.out.println(parts[0] + " --------" + parts[1]);
        reservedWordsNumber++;


        for (String sections : inputArray) {
            flag++;
            if (sections.equals("{")) {
                break;
            }
        }
        if (arrayMaxSize >= flag) {
            cntl1 = true;
            reservedWordsNumber++;
        }

        if (inputArray[arrayMaxSize-1].endsWith("}")) {
            cntl2 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true) {

            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");
        } else {
            System.out.println("Syntax error");
            return;
        }
    }

    public static void ifControl() {
        int flag = 0, flag2 = 0;
        int arrayMaxSize = inputArray.length;
        boolean cntl1 = false, cntl2 = false, cntl3 = false, cntl4 = false, cntl5 = false, cntl6 = false, cntl7 = false;
        reservedWordsNumber++;
        if (inputArray[1].equals("(")) {
            cntl1 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag++;
            if (sections.equals(")")) {
                break;
            }
        }
        if (arrayMaxSize >= flag) {
            cntl2 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag2++;
            if (sections.equals("{")) {
                break;
            }
        }
        if (arrayMaxSize >= flag2) {
            cntl3 = true;
            reservedWordsNumber++;
        }

        if (inputArray[arrayMaxSize-1].endsWith("}")) {
            cntl4 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true) {
            System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");
        } else {
            System.out.println("Syntax error");
            return;
        }
    }

    public static void switchControl() {
        int flag = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, countColons = 0, countCase = 0, countSemiColon = 0;
        int arrayMaxSize = inputArray.length;
        boolean cntl1 = false, cntl2 = false, cntl3 = false, cntl4 = false, cntl5 = false, cntl6 = false, cntl7 = false;
        reservedWordsNumber++;
        if (inputArray[1].equals("(")) {
            cntl1 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag++;
            if (sections.equals(")")) {
                break;
            }
        }
        if (arrayMaxSize >= flag) {
            cntl2 = true;
            reservedWordsNumber++;
        }

        for (String sections : inputArray) {
            flag2++;
            if (sections.equals(":")) {
                countColons++;
            }
        }

        if (arrayMaxSize >= flag2) {
            cntl3 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag3++;
            if (sections.equals("case")) {
                countCase++;
            }
        }
        if (arrayMaxSize >= flag3) {
            cntl4 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag4++;
            if (sections.equals(";")) {
                countSemiColon++;
            }
        }
        if (arrayMaxSize >= flag4) {
            cntl5 = true;
            reservedWordsNumber++;
        }

        for (String sections : inputArray) {
            flag5++;
            if (sections.equals("{")) {
                break;
            }
        }
        if (arrayMaxSize >= flag5) {
            cntl6 = true;
            reservedWordsNumber++;
        }

        if (inputArray[arrayMaxSize-1].endsWith("}")) {
            cntl7 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true && cntl5 == true && cntl6 == true && cntl7 == true && (countCase == countColons && countCase == countSemiColon) == true){
            System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println(countCase+" adet case,ikinokta ve noktali virgul bulunmustur.");
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");
        } else{
            System.out.println("Syntax error");
            return;
        }
    }

    public static void elseIfControl() {
        int flag = 0, flag2 = 0;
        int arrayMaxSize = inputArray.length;
        boolean cntl1 = false, cntl2 = false, cntl3 = false, cntl4 = false;
        reservedWordsNumber++;
        reservedWordsNumber++;
        if (inputArray[2].equals("(")) {
            cntl1 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag++;
            if (sections.equals(")")) {
                break;
            }
        }
        if (arrayMaxSize >= flag) {
            cntl2 = true;
            reservedWordsNumber++;
        }
        for (String sections : inputArray) {
            flag2++;
            if (sections.equals("{")) {
                break;
            }
        }
        if (arrayMaxSize >= flag2) {
            cntl3 = true;
            reservedWordsNumber++;
        }

        if (inputArray[arrayMaxSize-1].endsWith("}")) {
            cntl4 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true) {
            System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println("if" + " --------" + "if ayrilmis kelime");
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");
        } else {
            System.out.println("Syntax error");
            return;
        }
    }

}
