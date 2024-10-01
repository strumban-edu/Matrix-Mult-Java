import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class matrix_mult {
    public static Scanner scan = new Scanner(System.in);
    
    public static ArrayList<ArrayList<Integer>> matrixFromFile(String filename) {
        ArrayList<ArrayList<Integer>> matrix  = new ArrayList<>();

        while (true) {
            try {
                File file = new File(filename);
                Scanner fileReader = new Scanner(file);
                while (fileReader.hasNextLine()) {
                    String rawRow = fileReader.nextLine();
                    String[] seperatedRow = rawRow.split(" ");

                    ArrayList<Integer> fixRow = new ArrayList<>();
                    for (String num : seperatedRow) {
                        fixRow.add(Integer.parseInt(num));
                    }
                    matrix.add(fixRow);
                }

                fileReader.close();
                return matrix;
            } catch (FileNotFoundException f) {
                System.out.println("File '" + filename + "' could not be found or read.");
                System.out.println("Please ensure that file name was entered correctly and that the file exists.");
                System.out.println();
                matrix  = new ArrayList<>();
            } catch (NumberFormatException n) {
                System.out.println("File '" + filename + "' does not contain a valid matrix.");
                System.out.println("Please ensure that the given file contains a properly formatted matrix in text format.");
                System.out.println();
                System.out.println("Example of a properly formatted 3x2 matrix:");
                System.out.println("1 2 3");
                System.out.println("4 5 6");
                System.out.println();
                matrix  = new ArrayList<>();
            }

            System.out.println("If you would like to enter another file or if you have fixed the problem with your current file, you can retry.");
            System.out.println("Otherwise, you will be returned to the main menu.");
            System.out.println();
            while (true) {
                System.out.println("Would you like to retry opening a file?");
                System.out.print("Y/N: ");
                
                String usrInput = scan.nextLine();
                System.out.println();

                if (usrInput.toLowerCase().equals("y")) {
                    System.out.print("Enter the name of a text file that contains a valid matrix: ");
                    filename = scan.nextLine();
                    break;
                } else if (usrInput.toLowerCase().equals("n")) {
                    return matrix;
                } else {
                    System.out.println("'" + usrInput + "' is not a valid option.");
                    System.out.println();
                    System.out.println("Please enter either 'Y' to input a valid text file or 'N' to return to the main menu.");
                    System.out.println();
                }
            }
        }
    }

    public static void matrixToFile(String filename, ArrayList<ArrayList<Integer>> matrix, Integer matrixNum) {
        System.out.println("MATRIX " + matrixNum + ":");

        try {
            FileWriter fileCreator = new FileWriter(filename, false);
            fileCreator.close();
        } catch (IOException e) {
            System.out.println("An error occured while creating the output file.");
            System.out.println();
        }
        
        for (ArrayList<Integer> row : matrix) {
            System.out.println(row);
            try {
                FileWriter fileWriter = new FileWriter(filename, true);
                for (Integer num : row) {
                    fileWriter.write(Integer.toString(num) + " ");
                }
                fileWriter.write("\n");
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("An error occured while writing this row to the output file.");
            }
        }
    }

    public static void matrixDotProd(ArrayList<ArrayList<Integer>> matrix1, ArrayList<ArrayList<Integer>> matrix2) {
        Integer totalRowsM1 = matrix1.size();
        Integer totalRowsM2 = matrix2.size();
        Integer totalColumnsM1 = matrix1.get(0).size();
        Integer totalColumnsM2 = matrix2.get(0).size();

        if (totalColumnsM1 != totalRowsM2) {
            System.out.println("These two matrices are incompatible for multiplication!");
            System.out.println("Please ensure that the number of columns in matrix 1 is equivalent to the number of rows in matrix 2.");
            return;
        }

        ArrayList<ArrayList<Integer>> matrix3 = new ArrayList<>();
        for (Integer row = 0; row < totalRowsM1; row++) {
            ArrayList<Integer> arr = new ArrayList<>();
            for (Integer column = 0; column < totalColumnsM2; column++) {
                Integer dotProd = 0;
                for (Integer element = 0; element < totalColumnsM1; element++) {
                    dotProd += matrix1.get(row).get(element) * matrix2.get(element).get(column);
                }
                arr.add(dotProd);
            }
            matrix3.add(arr);
        }

        matrixToFile("matrix3.txt", matrix3, 3);
    }
    
    public static void main(String[] args) {
        System.out.println("----------------------------------------");
        System.out.println("--                                    --");
        System.out.println("--         MATRIX  MULTIPLIER         --");
        System.out.println("--     a-one Neo...  a-two Neo...     --");
        System.out.println("--                                    --");
        System.out.println("----------------------------------------");
        System.out.println();

        Random rand = new Random();
        while (true) {
            ArrayList<ArrayList<Integer>> matrix1 = new ArrayList<>();
            ArrayList<ArrayList<Integer>> matrix2 = new ArrayList<>();
            
            String usrInput;
            Boolean mainIsInt;

            if (args.length == 0) {
                System.out.println("Options:");
                System.out.println("1. Enter two valid filenames seperated by a space (ex: 'matrix1.txt matrix2.txt'). Both text files should contain valid matrices with values in the same row seperated by spaces.");
                System.out.println("2. Enter an integer (n). The program will generate two square nxn matrices. You will be prompted for the smallest and largest values that can be in these matrices, and they will then be populated with random numbers between these bounds.");
                System.out.println("3. You can run this program with command line arguments! To do this, simply include arguments (as specified in Options 2 and 3) following the program name. Ex: 'matrix_mult.jar matrix1.txt matrix2.txt' or 'matrix_mult.jar 5'.");
                System.out.println("4. Enter 'exit' to quit the program.");
                System.out.println();
                System.out.print("Input: ");
                mainIsInt = scan.hasNextInt();
                usrInput = scan.nextLine();
            } else {
                usrInput = String.join(" ", args);
                try {
                    Integer.parseInt(usrInput);
                    mainIsInt = true;
                } catch (NumberFormatException n) {
                    mainIsInt = false;
                }
                args = new String[]{};
            }
            
            if (usrInput.equals("exit")) {
                scan.close();
                break;
            } else if (mainIsInt) {
                Integer size = Integer.parseInt(usrInput);
                System.out.println();
                if (size <= 0) {
                    System.out.println("Array size must be a positive integer greater than 0.");
                    System.out.println();
                    continue;
                }

                System.out.println("Generating two random " + size + "x" + size + " matrices...");
                System.out.println();

                Integer low = 0;
                Integer high = -1;
                while (low > high) {
                    while (true) {
                        System.out.print("What's the smallest possible number you want in the matrices? ");
                        Boolean lowInt = scan.hasNextInt();
                        if (!lowInt) {
                            scan.nextLine();
                            System.out.println();
                            System.out.println("Please ensure that your input is a valid integer.");
                            System.out.println();
                            continue;
                        }

                        low = scan.nextInt();
                        scan.nextLine();
                        break;
                    }
                    System.out.println();

                    while (true) {
                        System.out.print("What's the largest possible number you want in the matrices? ");
                        Boolean highInt = scan.hasNextInt();
                        if (!highInt) {
                            scan.nextLine();
                            System.out.println();
                            System.out.println("Please ensure that your input is a valid integer.");
                            System.out.println();
                            continue;
                        }

                        high = scan.nextInt();
                        scan.nextLine();
                        break;
                    }

                    if (low > high) {
                        System.out.println();
                        System.out.println("Your lower bound, " + low + ", is larger then your upper bound, " + high + ".");
                        System.out.println("Please ensure the smallest possible number is less than the largest possible number.");
                    }
                    System.out.println();
                }

                for (Integer i = 0; i < size; i++) {
                    ArrayList<Integer> column1 = new ArrayList<>();
                    ArrayList<Integer> column2 = new ArrayList<>();
                    for (Integer j = 0; j < size; j++) {
                        Integer randNum1;
                        Integer randNum2;
                        if (low == high) {
                            randNum1 = low;
                            randNum2 = low;
                        } else {
                            randNum1 = rand.nextInt(high - low) + low;
                            randNum2 = rand.nextInt(high - low) + low;
                        }

                        column1.add(randNum1);
                        column2.add(randNum2);
                    }
                    matrix1.add(column1);
                    matrix2.add(column2);
                }

                matrixToFile("matrix1.txt", matrix1, 1);
                System.out.println();
                matrixToFile("matrix2.txt", matrix2, 2);
                System.out.println();
            } else if (usrInput.split(" ").length == 2) {
                String[] usrInputArr = usrInput.split(" ");

                matrix1 = matrixFromFile(usrInputArr[0]);
                if (matrix1.isEmpty()) {
                    continue;
                }

                System.out.println();
                matrix2 = matrixFromFile(usrInputArr[1]);
                if (matrix2.isEmpty()) {
                    continue;
                }

                System.out.println("MATRIX 1:");
                for (ArrayList<Integer> row : matrix1) {
                    System.out.println(row);
                }
                System.out.println();
        
                System.out.println("MATRIX 2:");
                for (ArrayList<Integer> row : matrix2) {
                    System.out.println(row);
                }
                System.out.println();
            } else {
                System.out.println("Your input '" + usrInput + "' was not a valid input.");
                System.out.println();
                continue;
            }

            System.out.println("Enter to reveal matrix product...");
            scan.nextLine();

            matrixDotProd(matrix1, matrix2);
            System.out.println();
        }

        System.out.println();
        System.out.println("Thank you for using the MATRIX MULTIPLIER!");
        System.out.println("Goodbye!");
    }
}
