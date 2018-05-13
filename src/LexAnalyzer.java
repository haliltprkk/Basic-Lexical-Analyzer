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
    public static ArrayList<keyWords> arrayListKeyWords = new ArrayList<>();
    public static ArrayList<keyWords> arrayListVariables = new ArrayList<>();


    static String[] parts;
    static String[] inputArray;
    static int reservedWordsNumber = 0;


    public static void readControlStatements() throws IOException {
        File f = new File("C:\\Users\\haliltprkk\\IdeaProjects\\LexAnalyzer\\src\\keyWords.txt");
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
        readVariableTypes();
        initializeVariableTypes();
        Scanner input = new Scanner(System.in);
        System.out.println("Type the code :");
        String data = input.nextLine();
        compareTheInput(data);
        // System.out.println(arrayListVariables.get(1).keywordName);

    }

    public static void compareTheInput(String data) {
        inputArray = splitTheInputWithSpace(data);
        int arrayMaxSize = inputArray.length - 1;
        int firstletterValue = inputArray[0].charAt(0);
        if ((firstletterValue >= 49) == true && (firstletterValue <= 57) == true) {  //ilk karakterin sayı olıp olmadığını kontrol ediyorum.
            System.out.println("first character can't be numeric!!");
        }

        boolean isIF = false, isSwitch = false, isElseIf = false, isElse = false, consistency = false, error = false, endingWithSemiColon = false, startWithVariableTypes = false;
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < arrayListControlStatements.size(); j++) {
                parts = arrayListControlStatements.get(j).split("-");  //burda dosyadan okuduğum veriyi kullanıcının girdiği verilerle karşılaştırıyorum
                if (inputArray[i].equals(parts[0])) {
                    consistency = true;
                    if (inputArray[0].equals("if") && isIF == false) {
                        isIF = ifControl();
                    }
                    if (inputArray[0].equals("else") && !inputArray[1].equals("if") && isElse == false) {
                        isElse = elseControl();
                    }
                    if (inputArray[0].equals("switch") && isSwitch == false) {
                        isSwitch = switchControl();
                    }
                    if (inputArray[0].equals("else") && inputArray[1].equals("if") && isElseIf == false) {
                        isElseIf = elseIfControl();
                    }
                    if (isElse == false && isElseIf == false && isIF == false && isSwitch == false) {
                        for (int k = 0; k < arrayListVariables.size(); k++) {
                            if (inputArray[i].equals(arrayListVariables.get(k).keywordName)) {
                                if (i != 0) {
                                    error = true;
                                    break;
                                }
                            }
                            if (inputArray[0].equals(arrayListVariables.get(k).keywordName)) {
                                if (inputArray.length < 5 && inputArray[2].equals("=")) {
                                    error = true;
                                    break;
                                }
                                if (inputArray[2].equals("=")) {
                                    if (inputArray[0].equals("float") || inputArray[0].equals("double") || inputArray[0].equals("int") || inputArray[0].equals("long")) {
                                        if (isNum(inputArray[3])) {
                                            startWithVariableTypes = true;
                                        } else {
                                            error = true;
                                            break;
                                        }
                                    } else {
                                        if (inputArray[3].startsWith("\"") && inputArray[3].endsWith("\"")) {
                                            startWithVariableTypes = true;
                                        } else {
                                            error = true;
                                            break;
                                        }

                                    }
                                    startWithVariableTypes = true;
                                } else if (!inputArray[2].equals("=") && inputArray.length == 3) {
                                    startWithVariableTypes = true;
                                    break;
                                } else {
                                    startWithVariableTypes = false;
                                    error = true;
                                    break;
                                }

                            }

                        }
                    }
                    if (isElse == true || isElseIf == true || isIF == true || isSwitch == true || startWithVariableTypes == true) {
                        arrayListKeyWords.add(new keyWords(parts[0], parts[1]));
                    }
                    //reservedWordsNumber++;
                    /*if (inputArray[arrayMaxSize].endsWith(";")) {
                        System.out.println(parts[0] + " --------" + "; noktali virgül");
                    } else {
                        System.out.println("Syntax error");
                    }*/
                }
            }
            if (isElse == false && isElseIf == false && isIF == false && isSwitch == false) {
                if (inputArray[arrayMaxSize].endsWith(";")) {
                    endingWithSemiColon = true;
                } else {
                    error = true;
                }
            }
            if (consistency == false && i - 2 == inputArray.length) {
                error = true;
            }
            if (arrayListKeyWords.size() == 1 && i - 2 == inputArray.length) {
                error = true;
            }
            //if (isIF) break;
        }
        if (arrayListKeyWords.size() == 0) {
            error = true;
        }
        if (error) {
            System.out.println("Syntax Error");
        } else {
            for (int i = 0; i < arrayListKeyWords.size(); i++) {
                System.out.println(arrayListKeyWords.get(i).getKeywordName() + " -------- " + arrayListKeyWords.get(i).getKeyWordExplanation());
            }
            //System.out.println(";" + " -------- " + "; noktali virgul");
        }
        /*System.out.println(inputArray.length - reservedWordsNumber + " adet kullanici tanimli ifade vardir.");
        System.out.println("-------------------");*/
    }

    public static String[] splitTheInputWithSpace(String data) {
        String inputArray[] = data.trim().split(" ");  //burda kullanıcının girdiği metni boşluklara göre ayırıp bir dizide tutuyorum.
        return inputArray;
    }

    public static ArrayList<keyWords> initializeVariableTypes() {
        for (int i = 0; i < arrayListVariableTypes.size(); i++) {
            String split[] = arrayListVariableTypes.get(i).split("-");
            arrayListVariables.add(new keyWords(split[0], split[1]));
        }
        return arrayListVariables;

    }


    public static boolean elseControl() {
        int flag = 0;
        int arrayMaxSize = inputArray.length;
        boolean cntl1 = false, cntl2 = false;
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

        if (inputArray[arrayMaxSize - 1].endsWith("}")) {
            cntl2 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true) {

            //System.out.println(parts[0] + " --------" + parts[1]);
            /*System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");*/
            return true;
        } else {
            // System.out.println("Syntax error");
            return false;
        }
    }

    public static boolean ifControl() {
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

        if (inputArray[arrayMaxSize - 1].endsWith("}")) {
            cntl4 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true) {
           /* System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");*/
            return true;
        } else {
            // System.out.println("Syntax error");
            return false;
        }
    }

    public static boolean switchControl() {
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

        if (inputArray[arrayMaxSize - 1].endsWith("}")) {
            cntl7 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true && cntl5 == true && cntl6 == true && cntl7 == true && (countCase == countColons && countCase == countSemiColon) == true) {
           /* System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println(countCase + " adet case,iki nokta ve noktali virgul bulunmustur.");
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");*/
            return true;
        } else {
            //   System.out.println("Syntax error");
            return false;
        }
    }

    public static boolean elseIfControl() {
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

        if (inputArray[arrayMaxSize - 1].endsWith("}")) {
            cntl4 = true;
            reservedWordsNumber++;
        }
        if (cntl1 == true && cntl2 == true && cntl3 == true && cntl4 == true) {
           /* System.out.println(parts[0] + " --------" + parts[1]);
            System.out.println("if" + " --------" + "if ayrilmis kelime");
            System.out.println("(" + " --------" + "( acik parantez");
            System.out.println(")" + " --------" + ") kapali parantez");
            System.out.println("{" + " --------" + "{ acik süslü parantez");
            System.out.println("}" + " --------" + "} kapali süslü parantez");*/
            return true;
        } else {
            //     System.out.println("Syntax error");
            return false;
        }
    }

    public static boolean isNum(String strNum) {
        boolean ret = true;
        try {

            Double.parseDouble(strNum);

        } catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }
}
