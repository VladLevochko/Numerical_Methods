package rgr;

import Jama.*;
import com.sun.xml.internal.ws.client.dispatch.PacketDispatch;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by vlad on 15.05.16.
 */
public class MatrixSolversComparator {
    public  static void main(String args[]) {
        String path = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_5_ 5_cage3.txt";
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
            rgr.Matrix rgrM = new rgr.Matrix(A);

            long startJama = System.currentTimeMillis();
            EigenvalueDecomposition evd = new EigenvalueDecomposition(jamaM);
            double[] ev = evd.getRealEigenvalues();
            long finishJama = System.currentTimeMillis() - startJama;
            out.println("jama finish in " + finishJama + "ms");
//            for (int i = 0; i < n; i++) {
//                out.println(ev[i]);
//            }

            long startRGR = System.currentTimeMillis();
            double[] evm = rgrM.getEigenVector();
            long finishRGR = System.currentTimeMillis() - startRGR;
            out.println("rgr finish in " + finishRGR + "ms");


//            Arrays.sort(ev);
//            Arrays.sort(evm);
            for (int i = 0; i < evm.length; i++) {
                if (ev[i] != Math.abs(evm[i])) {
                    out.println(ev[i] + "\t\t" + evm[i]);
                }
            }
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
