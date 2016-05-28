package rgr;

import Jama.*;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Created by vlad on 15.05.16.
 */
public class MatrixSolversComparator {
    public  static void main(String args[]) {
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_5_ 5_cage3.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparce_7_7_b1_ss.txt";
        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_10_10_Stranke94.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_27_27_ex5.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_100_100.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_105_105.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_135_135.txt";
//        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_180_180.txt";



        System.out.println(path);
        try (Scanner in = new Scanner(new File(path));
             PrintWriter out = new PrintWriter(System.out)) {
            int n = in.nextInt();
            int m = in.nextInt();
            double[][] a = new double[n][m];
            List<List<Double>> A = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                ArrayList<Double> t = new ArrayList<>();
                for (int j = 0; j < m; j++) {
                    double d = in.nextDouble();
                    a[i][j] = d;
                    t.add(d);
                }
                A.add(t);
            }
            Jama.Matrix jamaM = new Jama.Matrix(a, m, n);
            Matrix rgrM = new Matrix(a, n, m);

            long startJama = System.currentTimeMillis();
            EigenvalueDecomposition evd = new EigenvalueDecomposition(jamaM);
            double[] ev = evd.getRealEigenvalues();
            long finishJama = System.currentTimeMillis() - startJama;
            out.println("jama finish in " + finishJama + "ms");
//            for (int i = 0; i < n; i++) {
//                out.println(ev[i]);
//            }

            long startRGR = System.currentTimeMillis();
            double[] evm = rgrM.eiv();
            long finishRGR = System.currentTimeMillis() - startRGR;
            out.println("rgr finish in " + finishRGR + "ms");


            Arrays.sort(ev);
            Arrays.sort(evm);



            DecimalFormat format = new DecimalFormat();
            format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            format.setMinimumIntegerDigits(1);
            format.setMaximumFractionDigits(8);
            format.setMinimumFractionDigits(8);
            format.setGroupingUsed(false);

            String s;
            for (int i = 0; i < evm.length; i++) {
                s = format.format(ev[i]);
                int padding = Math.max(1, 15 - s.length()); // At _least_ 1 space
                for (int k = 0; k < padding; k++)
                    System.out.print(' ');
                System.out.print(s);

                s = format.format(evm[i]);
                padding = Math.max(1, 10 - s.length()); // At _least_ 1 space
                for (int k = 0; k < padding; k++)
                    System.out.print(' ');
                System.out.print(s);

                s = format.format(ev[i] - evm[i]);
                padding = Math.max(1, 10 - s.length()); // At _least_ 1 space
                for (int k = 0; k < padding; k++)
                    System.out.print(' ');
                System.out.println(s);
            }

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
