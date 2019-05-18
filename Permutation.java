import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Permutation {

    public static ArrayList<String> permutation_list;
    String decrypted_text;
    String encrypted_text;
    String plainText;
    String encryptToDecrypt;
    ArrayList<String> matching_words = new ArrayList<>();


    public Permutation() {
        permutation_list = new ArrayList();
        plainText = "";
        encrypted_text = "";
        /*matching_words.add("CHECKPOINT");
        matching_words.add("HEADQUARTER");
        matching_words.add("ATTENTION");
        matching_words.add("CONTACT");*/

    }

    //* CIPHER TEXT TO 2D MATRIX *//*
    public char[][] textTo2Dmatrix(String cipherText, int key_order_size) {
        int rowNo = cipherText.length() / key_order_size; //25

        char matrix[][] = new char[rowNo + 5][key_order_size + 1];

        for (int col = 0; col < key_order_size; col++) {

            for (int row = 0; row < rowNo; row++) {
                matrix[row][col] = cipherText.charAt((col) * rowNo + row);
                //System.out.print(matrix[row][col]);
            }
            //System.out.println();
        }
        return matrix;

    }

    //* plain TEXT TO cipher 2D MATRIX *//*
    public char[][] plainTextTo2Dmatrix(String PlainText, int rowNo) {
        int colNo = PlainText.length() / rowNo; //25

        char matrix[][] = new char[rowNo + 1][colNo + 5];

        for (int col = 0; col < colNo; col++) {
            for (int row = 0; row < rowNo; row++) {

                matrix[row][col] = PlainText.charAt((row) * colNo + col);
                //System.out.print(matrix[row][col]);
            }
            // System.out.println();
        }

        return matrix;

    }

    public void getPlainTextFromKeyOrdering(char[][] matrix, ArrayList<Character> key_order) {

        plainText = "";
        int col;
        for (int row = 0; row < 25; row++) {
            for (int itr = 0; itr < 5; itr++) {

                //System.out.println(key_order.get(itr));
                col = key_order.get(itr) - '0' - 1;
                //System.out.println("col : " + col + " itr : " +itr);
                plainText += matrix[row][col];
                //System.out.print(matrix[row][col]);

            }
            // System.out.println();

        }
    }

    public ArrayList<Character> decryption(ArrayList<Character> key_order, String cipherText, BufferedWriter bufferedWriter) {

        decrypted_text = "";
        key_order.clear();

        for (int i = 0; i < permutation_list.size(); i++) {
            //System.out.println("ITERATION : " + i + " = " + permutation_list.get(i));
            char col1 = permutation_list.get(i).charAt(0);
            char col2 = permutation_list.get(i).charAt(1);
            char col3 = permutation_list.get(i).charAt(2);
            char col4 = permutation_list.get(i).charAt(3);
            char col5 = permutation_list.get(i).charAt(4);

            key_order.add(col1);
            key_order.add(col2);
            key_order.add(col3);
            key_order.add(col4);
            key_order.add(col5);

            /*key_order.add('1');
            key_order.add('5');
            key_order.add('3');
            key_order.add('4');
            key_order.add('2');*/
            char matrix[][];//[rowNo + 5][key_order_size + 1];
            matrix = textTo2Dmatrix(cipherText, 5);
            getPlainTextFromKeyOrdering(matrix, key_order);


            //find in string the matching words
            /*System.out.println(permutation.plainText.contains(matching_words.get(0)));
            System.out.println(permutation.plainText.contains(matching_words.get(1)));
            System.out.println(permutation.plainText.contains(matching_words.get(2)));
            System.out.println(permutation.plainText.contains(matching_words.get(3)));*/

            if (plainText.contains(matching_words.get(0))
                    && plainText.contains(matching_words.get(1))
                    && plainText.contains(matching_words.get(2))
                    && plainText.contains(matching_words.get(3))) {

                try {
                    bufferedWriter.write("\n\nMATCH FOUND! At Iteration : " + i + "\n");
                    bufferedWriter.write("\nDecrypted text is : " + plainText.toLowerCase() + "\n");
                    bufferedWriter.write("\nKey Order : " + col1 + " " + col2 + " " + col3 + " " + col4 + " " + col5 + "\n");

                } catch (Exception e) {

                }

                System.out.println("\n\nMATCH FOUND! At Iteration : " + i + "\n");
                System.out.println("Decrypted text is : " + plainText.toLowerCase());
                decrypted_text = plainText;
                System.out.println(col1 + " " + col2 + " " + col3 + " " + col4 + " " + col5);
                return key_order;//cipherText = line;
            }

            //clearing previous values
            key_order.clear();
            plainText = "";
        }
        return null;
    }

    public void encryption(char[][] matrix, ArrayList<Character> key_order) {

        encrypted_text = "";

        char matrix2[][] = new char[25 + 5][5 + 1];//[rowNo + 5][key_order_size + 1]
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 25; i++) {

                matrix2[i][key_order.get(j) - '0' - 1] = matrix[i][j];
            }
        }

        System.out.println();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 25; j++) {
                encrypted_text += matrix2[j][i];
                //System.out.print(matrix2[j][i]);
            }
            //System.out.println();
        }

        //System.out.println(encrypted_text);
    }

    public static void main(String[] args) {


        String str = "12345";
        int n = str.length();
        Permutation permutation = new Permutation();
        permutation.permute(str, 0, n - 1);


        //permutation.printList(permutation_list);

        String cipherText = "";
        permutation.plainText = "";
        ArrayList<Character> key_order = new ArrayList<>();
        // String directory = System.getProperty("user.home");
        String out_fileName = "output.txt";
        String in_fileName = "input.txt";
        String absolutePath = "/home/afsara/IdeaProjects/transposition_cipher/src/" + out_fileName;
        String absoluteReadPath = "/home/afsara/IdeaProjects/transposition_cipher/src/" + in_fileName;

        //read content from file
        PrintStream outFile;
        File file;
        Scanner sc;
        String line[] = new String[7];

        try {

            file = new File(absoluteReadPath);
            sc = new Scanner(file);
            int i = 0;
            while (sc.hasNextLine())
            {
                line[i] = sc.nextLine();
                System.out.println(i + " : " + line[i]);
                i++;
            }
            cipherText = line[0];
            String [] tokens = line[2].split(",");

            for (int j = 0; j < tokens.length; j++) {
                tokens[j].replaceAll(" ","");
            }
            permutation.matching_words.add(tokens[0].toUpperCase());
            permutation.matching_words.add(tokens[1].toUpperCase());
            permutation.matching_words.add(tokens[2].toUpperCase());
            permutation.matching_words.add(tokens[3].toUpperCase());

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }


        // write the content in file
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(absolutePath))) {


            System.out.println(" ~~~~~~~~~~~~~~~~~~~~~~~~~ ORIGINAL CIPHER TEXT ~~~~~~~~~~~~~~~~~~~~~~~~~  ");

            //cipherText = "TLRLADOEAAINWRETSCNDTFTIDEOITTMHZEOOTBHNAEIARNATOXEAEAKWEATTTATVSECPHURLENXDFWTANTNOPCCHOIEHOEAILNMXHTCLCAVPRSNTIAIHTKTQEUTOE";

            bufferedWriter.write("Original Cipher Text : \n" + cipherText + "\n");
            System.out.println(cipherText);

            System.out.println(" \n~~~~~~~~~~~~~~~~~~~~~~~~~ STARTING DECRYPTION ~~~~~~~~~~~~~~~~~~~~~~~~~  \n");

            permutation.decryption(key_order, cipherText, bufferedWriter);
            //System.out.println("Decrypted text is : " + permutation.decrypted_text);

            //decryption done above, encryption starts
            System.out.println(" \n~~~~~~~~~~~~~~~~~~~~~~~~~ STARTING ENCRYPTION ~~~~~~~~~~~~~~~~~~~~~~~~~  \n");
            char matrix[][] = permutation.plainTextTo2Dmatrix(permutation.decrypted_text, 25);
            permutation.encryption(matrix, key_order);
            System.out.println("Encrypted text is : " + permutation.encrypted_text);
            bufferedWriter.write("\nEncrypted text is : " + permutation.encrypted_text + "\n");

            float accuracy = permutation.calculateDifference(cipherText, permutation.encrypted_text, cipherText.length());
            accuracy = (accuracy / cipherText.length()) * 100;
            System.out.println(accuracy);
            bufferedWriter.write("Accuracy is : " + accuracy);

        } catch (IOException e) {
            // exception handling
        }

    }

    public int calculateDifference(String w1, String w2, int len) {
        String s1, s2;
        int counter = w1.length();
        for (int j = 0; j < len; j++) {
            s1 = w1.substring(j, j + 1);
            s2 = w2.substring(j, j + 1);

            if (!s1.equalsIgnoreCase(s2)) {
                counter--;

            }
        }
        return counter;
    }

    /**
     * permutation function
     *
     * @param str string to calculate permutation for
     * @param l   starting index
     * @param r   end index
     */
    private void permute(String str, int l, int r) {
        if (l == r) {
            permutation_list.add(str);
            //System.out.println(str);

        } else {
            for (int i = l; i <= r; i++) {
                str = swap(str, l, i);
                permute(str, l + 1, r);
                str = swap(str, l, i);

            }
        }
    }

    /**
     * Swap Characters at position
     *
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    public String swap(String a, int i, int j) {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    public void printList(ArrayList lst) {
        for (int i = 0; i < lst.size(); i++) {
            System.out.println(lst.get(i));
        }
    }

}
