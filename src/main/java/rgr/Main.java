package rgr;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by vlad on 04.05.16.
 */
public class Main {

    private static Map<String, Matrix> matrixMap;
    private static String[] splitCommand;
    private static Scanner in;

    private static void  addNewMatrix() {
        try {
            if (splitCommand.length < 2) {
                System.out.println("incorrect input! no matrix name");
            }
            if (matrixMap.containsKey(splitCommand[1])) {
                String accept = "";
                System.out.println("matrix with name already exist. rewrite matrix?\ny/n");
                while ("".equals(accept)) {
                    accept = in.nextLine();
                }
                if (!accept.equals("y")) {
                    return;
                }
            }
            if (splitCommand.length < 3) {
                System.out.println("incorrect input! no path to file");
            }
            matrixMap.put(splitCommand[1], new Matrix(splitCommand[2]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void remove() {
        if (splitCommand.length > 1) {
            try {
                if (matrixMap.containsKey(splitCommand[1])) {
                    String accept = "";
                    System.out.println("completely remove matrix" + splitCommand[1] + "?\ny/n");

                    while ("".equals(accept)) {
                        accept = in.nextLine();
                    }
                    if (accept.equals("y")) {
                        matrixMap.remove(splitCommand[1]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("incorrect command! enter matrix name");
        }

    }

    private static void showAll() {
        for (Map.Entry<String, Matrix> entry : matrixMap.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().print();
        }

    }

    private static void show() {
        if (splitCommand.length > 1) {
            if (matrixMap.containsKey(splitCommand[1])) {
                System.out.println(splitCommand[1]);
                matrixMap.get(splitCommand[1]).print();
            } else {
                System.out.println("have no matrix with this name");
            }
        } else {
            System.out.println("incorrect command! enter matrix name");
        }

    }

    private static void add() {
        if (splitCommand.length > 2) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (!matrixMap.containsKey(splitCommand[2])) {
                System.out.println("have no matrix " + splitCommand[2]);
                return;
            }
            if (splitCommand.length > 3) {
                Matrix result = matrixMap.get(splitCommand[1]).add(matrixMap.get(splitCommand[2]));
                result.print();
                matrixMap.put(splitCommand[3], result);
            } else {
                matrixMap.get(splitCommand[1]).add(matrixMap.get(splitCommand[2])).print();
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void sub() {
        if (splitCommand.length > 2) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (!matrixMap.containsKey(splitCommand[2])) {
                System.out.println("have no matrix " + splitCommand[2]);
                return;
            }
            if (splitCommand.length > 3) {
                Matrix result = matrixMap.get(splitCommand[1]).subtract(matrixMap.get(splitCommand[2]));
                result.print();
                matrixMap.put(splitCommand[3], result);
            } else {
                matrixMap.get(splitCommand[1]).subtract(matrixMap.get(splitCommand[2])).print();
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void multiply() {
        if (splitCommand.length > 2) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (!matrixMap.containsKey(splitCommand[2])) {
                System.out.println("have no matrix " + splitCommand[2]);
                return;
            }
            if (splitCommand.length > 3) {
                try {
                    Matrix result = matrixMap.get(splitCommand[1]).multiply(matrixMap.get(splitCommand[2]));
                    result.print();
                    matrixMap.put(splitCommand[3], result);
                } catch (MatrixException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    matrixMap.get(splitCommand[1]).multiply(matrixMap.get(splitCommand[2])).print();
                } catch (MatrixException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void getR() {
        if (splitCommand.length > 1) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (splitCommand.length > 2) {
                Matrix result = QR.householder(matrixMap.get(splitCommand[1])).getR();
                result.print();
                matrixMap.put(splitCommand[2], result);
            } else {
                QR.householder(matrixMap.get(splitCommand[1])).getR().print();
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void getQ() {
        if (splitCommand.length > 1) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (splitCommand.length > 2) {
                Matrix result = QR.householder(matrixMap.get(splitCommand[1])).getQ();
                result.print();
                matrixMap.put(splitCommand[2], result);
            } else {
                QR.householder(matrixMap.get(splitCommand[1])).getQ().print();
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void getRQ() {
        if (splitCommand.length > 1) {
            if (!matrixMap.containsKey(splitCommand[1])) {
                System.out.println("have no matrix " + splitCommand[1]);
                return;
            }
            if (splitCommand.length > 2) {
                Matrix result = QR.householder(matrixMap.get(splitCommand[1])).getRQ();
                result.print();
                matrixMap.put(splitCommand[2], result);
            } else {
                QR.householder(matrixMap.get(splitCommand[1])).getRQ().print();
            }
        } else {
            System.out.println("incorrect command");
        }

    }

    private static void getEV() {
        if (matrixMap.containsKey(splitCommand[1])) {
            double[] ev = matrixMap.get(splitCommand[1]).eiv();
            for (int i = 0; i < ev.length; i++) {
                System.out.println(ev[i]);
            }
        } else {
            System.out.println("have no matrix " + splitCommand[1]);
        }

    }





    public static void main(String args[]) {

        /*
         commands:
                add new matrix
                    addNew 'matrixName' 'pathToMatrix'
                remove matrix
                    remove 'matrixName'
                show matrix
                    show 'matrixName'
                show all matrices
                    showAll

                add matrices
                    add matrix1 matrix2
                    add matrix1 matrix2 newMatrixName
                subtract matrices
                    sub matrix1 matrix2
                    sub matrix1 matrix2 newMatrixName
                multiply matrices
                    multiply matrix1 matrix2
                    multiply matrix1 matrix2 newMatrixName

                QR
                getR
                    getr matrix
                    getr matrix toMatrix
                getQ
                    getq matrix
                    getq matrix toMatrix
                getRQ
                    getrq matrix
                    getrq toMatrix
                get eigenvector
                    ev matrix
         */

        System.out.println("" +
                "commands:\n" +
                "                add new matrix\n" +
                "                    addNew 'matrixName' 'pathToMatrix'\n" +
                "                remove matrix\n" +
                "                    remove 'matrixName'\n" +
                "                show matrix\n" +
                "                    show 'matrixName'\n" +
                "                show all matrices\n" +
                "                    showAll\n" +
                "                \n" +
                "                add matrices\n" +
                "                    add matrix1 matrix2\n" +
                "                    add matrix1 matrix2 newMatrixName\n" +
                "                subtract matrices\n" +
                "                    sub matrix1 matrix2\n" +
                "                    sub matrix1 matrix2 newMatrixName\n" +
                "                multiply matrices\n" +
                "                    multiply matrix1 matrix2\n" +
                "                    multiply matrix1 matrix2 newMatrixName\n" +
                "\n" +
                "                QR\n" +
                "                getR\n" +
                "                    getr matrix\n" +
                "                    getr matrix toMatrix\n" +
                "                getQ\n" +
                "                    getq matrix\n" +
                "                    getq matrix toMatrix\n" +
                "                getRQ\n" +
                "                    getrq matrix\n" +
                "                    getrq toMatrix\n" +
                "                get eigenvector\n" +
                "                    ev matrix\n" +
                "         ");

        try (Scanner in = new Scanner(System.in)) {
            String command = "";
            matrixMap = new HashMap<>();

            boolean repeat = true;
            while (repeat) {
                System.out.print("> ");
                command = in.nextLine();
                if (command.length() == 0) {
                    continue;
                }
                splitCommand = command.split(" ");
                switch (splitCommand[0]) {

                    case ("addnew"):
                        addNewMatrix();
                        break;

                    case ("remove"):
                        remove();
                        break;

                    case ("showall"):
                        showAll();
                        break;

                    case ("show"):
                        show();
                        break;

                    case ("add"):
                        add();
                        break;

                    case ("sub"):
                        sub();
                        break;

                    case ("multiply"):
                        multiply();
                        break;

                    case ("getr"):
                        getR();
                        break;

                    case ("getq"):
                        getQ();
                        break;

                    case ("getrq"):
                        getRQ();
                        break;

                    case ("ev"):
                        getEV();
                        break;
                    case ("exit"):
                        repeat = false;
                        break;

                    default:
                        System.out.println("incorrect command");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
