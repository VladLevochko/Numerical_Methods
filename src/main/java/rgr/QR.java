package rgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vlad on 03.05.16.
 */
public class QR {

    public static QRDecomposition householder(Matrix A) {
        //Ak = Pk * Ak-1
        //P = (E - 2 * Wi * transWi)

        //An = R
        //Q = P1 * P2 * ... * Pn

        //Wi = ui * (aii - si, a2i, ... , ani)
        //si = Sum[(aji)^2, {j, i, n}]^(1/2)
        //ui = [2 * si * (si - aii)]^(-1/2)

        int n = A.getN();
        Matrix Q = null;
        Matrix P;
        Matrix W, transposeW;
        IdentityMatrix E = new IdentityMatrix(A.getN());
        for (int i = 0; i < A.getM(); i++) {
            //calculate s
            double[] curColumn = A.getColumn(i);
            double s = 0;
            for (int j = i; j < n; j++) {
                s += curColumn[j] * curColumn[j];
            }
            s = Math.sqrt(s);
            //get converse sign of diagonal element
            if (A.get(i, i) != 0) {
                s *= Math.signum(A.get(i, i)) * -1.;
            }

            //calculate u
            double u = 1. / Math.sqrt(2. * s * (s - A.get(i, i)));

            //calculate W
            W = new Matrix(n, 1);
            for (int j = 0; j < n; j++) {
                if (j < i)
                    W.set(j, 0, 0);
                else
                    if (i == j)
                        W.set(j, 0, u * (A.get(j, i) - s));
                    else
                        W.set(j, 0, u * A.get(j, i));
            }

            transposeW = W.transpose();

            try {
                //calculate P = (E - 2 * Wi * transWi)
                P = E.subtract(W.multiply(2.).multiply(transposeW));

                //calculate next A
                A = P.multiply(A);

                //Q = Pk * Pk-1 * ... * P1
                if (Q == null) {
                    Q = P;
                } else {
                    Q = Q.multiply(P);
                }

                System.out.println("A matrix" + i);
                A.print();
                System.out.println("Q matrix" + i);
                Q.print();
                System.out.println("P matrix" + i);
                P.print();
                System.out.println("s " + s + " u" + u);
                System.out.println();

            } catch (MatrixException e) {
                e.printStackTrace();
            }

            //check for upper triangular matrix
            if (A.isUpperTriangular())
                break;
        }

        return new QRDecomposition(Q, A);
    }

    public static QRDecomposition givensRotation(Matrix A) {
        //Ak = transQk * Ak-1
        /*
            |1     |
            | c -s |
          Q=|  1   |
            | s  c |
            |     1|

            c = ajj / sqrt(ajj^2 + aij^2)
            s = aij / sqrt(ajj^2 + aij^2)
         */

        int n = A.getM();
        boolean stop = false;
        int k = 0;
        Matrix Q = null;
        for (int i = 0; i < n; i++) {
            if (!stop)
                for (int j = i + 1; j < n; j++) {
                    Matrix curQ = new IdentityMatrix(n);
                    //calculate c and s
                    double c = A.get(i, i)  / Math.sqrt(A.get(i,i) * A.get(i,i) + A.get(j, i) * A.get(j, i));
                    double s = A.get(j, i)  / Math.sqrt(A.get(i,i) * A.get(i,i) + A.get(j, i) * A.get(j, i));
                    //set c and s in matrix Q
                    curQ.set(i, i, c);
                    curQ.set(j, j, c);
                    curQ.set(i, j, -s);
                    curQ.set(j, i, s);
                    //transpose Q
                    Matrix tQ = curQ.transpose();
                    //calculate  Ak
                    try {
                        A = tQ.multiply(A);
                        if (Q == null) {
                            Q = tQ;
                        } else {
                            Q = tQ.multiply(Q);
                        }
                    } catch (MatrixException e) {
                        e.printStackTrace();
                    }

//                    //----------------
//                    System.out.println(++k + "\nmatrix A");
//                    A.print();
//                    System.out.println("matrix Q");
//                    curQ.print();

                    //check for upper triangular matrix
//                    if (A.isUpperTriangular()) {
//                        stop = true;
//                        Q = Q.transpose();
//                        break;
//                    }
                }
        }
//        System.out.println("matrix Q");
//        Q.print();
        return new QRDecomposition(Q, A);
    }



    public static void main(String args[]){
        List<List<Double>> arrayForM = new ArrayList<>();
        arrayForM.add(Arrays.asList(2., 1., 4., 1.));
        arrayForM.add(Arrays.asList(3., 3., 2., -2.));
        arrayForM.add(Arrays.asList(4., 2., -1., 3.));
        arrayForM.add(Arrays.asList(5., -1., 4., 2.));

//        List<List<Double>> arrayForM = new ArrayList<>();
//        arrayForM.add(Arrays.asList(12., -51., 4.));
//        arrayForM.add(Arrays.asList(6., 167., -68.));
//        arrayForM.add(Arrays.asList(-4., 24., -41.));

//        List<List<Double>> arrayForM = new ArrayList<>();
//        arrayForM.add(Arrays.asList(1., -1., 4.));
//        arrayForM.add(Arrays.asList(1., 4., -2.));
//        arrayForM.add(Arrays.asList(1., 4., 2.));
//        arrayForM.add(Arrays.asList(1., -1., 0.));
//OK

//        List<List<Double>> arrayForM = new ArrayList<>();
//        arrayForM.add(Arrays.asList(0.8147, 0.0975, 0.1576));
//        arrayForM.add(Arrays.asList(0.9058, 0.2785, 0.9706));
//        arrayForM.add(Arrays.asList(0.1270, 0.5469, 0.9572));
//        arrayForM.add(Arrays.asList(0.9134, 0.9575, 0.4854));
//        arrayForM.add(Arrays.asList(0.6324, 0.9649, 0.8003));
//
        Matrix M = new Matrix(arrayForM);

        System.out.println("householder");
        QRDecomposition result = QR.householder(M);
        result.print();
//
//        System.out.println("RQ");
//        result.getRQ().print();
//        System.out.println("QR");
//        result.getQR().print();

//        QRDecomposition dec = QR.givensRotation(M);
//        System.out.println("givens 1");
//        dec.print();
//        System.out.println("rq");
//        dec.getRQ().print();
//        QRDecomposition res = QR.givensRotation(dec.getRQ());
//        System.out.println("givens");
//        res.print();

//        List<Double> ev = M.getEigenVector();
//        List<List<Double>> l = new ArrayList<>();
//        for (Double d : ev) {
//            List<Double> t = new ArrayList<>();
//            t.add(d);
//            l.add(t);
//        }
//        Matrix x = new Matrix(l);
//        try {
//            Matrix B = M.multiply(x);
//            B.print();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Matrix m = new Matrix("/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_100_100.txt");
//            long s = System.currentTimeMillis();
//            double[] t = m.getEigenVector();
//            System.out.println("time " + (System.currentTimeMillis() - s));
////            for (int i = 0; i < t.length; i++) {
////                System.out.println(t[i]);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}