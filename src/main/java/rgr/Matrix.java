package rgr;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

/**
 * Created by vlad on 02.05.16.
 */
public class Matrix {

    protected int N = 0;
    protected int M = 0;
    protected int d = 4;
    protected int w = 8;

    protected double[][] A;


    public Matrix(int n, int m) {
        this.N = n;
        this.M = m;
        A = new double[n][m];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                A[i][j] = 0;
            }
        }
    }

    public Matrix(List<List<Double>> matrix) {
        this.N = matrix.size();
        this.M = matrix.get(0).size();
        A = new double[N][M];

        List<Double> t;
        for (int i = 0; i < N; i++) {
            t = matrix.get(i);
            for (int j = 0; j < M; j++) {
                A[i][j] = t.get(j);
            }
        }
    }

    public Matrix(double[][] A, int n, int m) {
        this.A = A;
        this.N = n;
        this.M = m;
    }

    public Matrix(String path) throws MatrixException {
        Scanner in;
        try {
            in = new Scanner(new File(path));
            N = in.nextInt();
            M = in.nextInt();

            A = new double[N][M];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    A[i][j] = in.nextDouble();
                }
            }
            System.out.println("matrix was successfully read");
        } catch (IOException e) {
            throw new MatrixException("error reading matrix");
        }
    }

    public Matrix multiply(Matrix B) throws MatrixException {
        if (this.getM() != B.getN())
            throw new MatrixException("incorrect matrix multiplication");

        int resN = this.getN();
        int resM = B.getM();
        Matrix result = new Matrix(resN, resM);

        double[] currentRow;
        double[] currentColumn;
        for (int i = 0; i < resN; i++) {
            //get row i from A matrix
            currentRow = this.getRow(i);
            for (int j = 0; j < resM; j++) {
                //get column j from B matrix
                currentColumn = B.getColumn(j);
                double sum = 0;
                //calculate result[i,j]
                for (int k  = 0; k < currentRow.length; k++)
                    sum += currentRow[k] * currentColumn[k];
                //exclude -0.0000
                if (Math.abs(sum) <= 1E-12)
                    result.set(i, j, 0);
                else
                    result.set(i, j, sum);
            }
        }

        return result;
    }

    public Matrix multiply(double t) {
        Matrix result = new Matrix(this.getN(), this.getM());

        for (int i = 0; i < this.getN(); i++) {
            double[] row = this.getRow(i);
            for (int j = 0; j < this.getM(); j++)
                result.set(i, j, row[j] * t);
        }

        return result;
    }

    public Matrix add(Matrix B) {
        Matrix result = new Matrix(this.getN(), this.getM());

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                result.set(i, j, this.get(i, j) + B.get(i, j));
            }
        }

        return result;
    }

    public Matrix subtract(Matrix B) {
        Matrix result = new Matrix(this.getN(), this.getM());

        for (int i = 0; i < this.getN(); i++) {
            for (int j = 0; j < this.getM(); j++) {
                result.set(i, j, this.get(i, j) - B.get(i, j));
            }
        }

        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.getM(), this.getN());

        for (int i = 0; i < this.getM(); i++) {
            for (int j = 0; j < this.getN(); j++) {
                result.set(i, j, this.get(j, i));
            }
        }

        return result;
    }

    public void set(int i, int j, double value) {
        A[i][j] = value;
    }

    public double get(int i, int j) {
        return A[i][j];
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public double[] getColumn(int columnNumber) {
        double[] column = new double[N];
        for (int i = 0; i < this.N; i++) {
            column[i] = A[i][columnNumber];
        }
        return column;
    }

    public double[] getRow(int rowNumber) {
        double[] row = new double[M];
        for (int i = 0; i < this.M; i++) {
            row[i] = A[rowNumber][i];
        }
        return row;
    }

    public void print() {
        for (int i = 0; i < this.N; i++) {
            printRow(i);
            System.out.println();
        }
    }

    private void printRow(int rowNumber) {

        DecimalFormat format = new DecimalFormat();
        format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
        format.setMinimumIntegerDigits(1);
        format.setMaximumFractionDigits(d);
        format.setMinimumFractionDigits(d);
        format.setGroupingUsed(false);

        System.out.print("|");
        for (int i = 0; i < this.getM(); i++) {
            String s = format.format(A[rowNumber][i]);
            int padding = Math.max(1, w - s.length()); // At _least_ 1 space
            for (int k = 0; k < padding; k++)
                System.out.print(' ');
            System.out.print(s);
        }
        System.out.print("|");
    }

    public boolean isUpperTriangular(){
        boolean ans = true;
        for (int i = 0; i < N; i++) {
            if (!ans)
                break;
            for (int j = 0; j < Math.min(i, M); j++) {
                if (Math.abs(this.get(i, j)) > 1E-14) {
                    ans = false;
                    break;
                }
            }
        }

        return ans;
    }

    public double[] getEigenVector() {
        double[] result = new double[N];

        Matrix R = QR.householder(this).getR();
        for (int i = 0; i < R.getM(); i++) {
            result[i] = R.get(i, i);
        }

        return result;
    }

    public static void main(String args[]) {
        List<List<Double>> arrayForA = new ArrayList<> ();
        arrayForA.add(Arrays.asList(2., 3.));
        arrayForA.add(Arrays.asList(4., 5.));
        List<List<Double>> arrayForB = new ArrayList<> ();
        arrayForB.add(Arrays.asList(6., 7.));
        arrayForB.add(Arrays.asList(8., 9.));

        Matrix a = new Matrix(arrayForA);
        Matrix b = new Matrix(arrayForB);
        try {
            Matrix c = a.multiply(b);
            c.print();

            c.multiply(-1);
            c.print();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Matrix d = a.transpose();
        d.print();

//        Matrix a = new Matrix(arrayForA);
//        System.out.println(a.getEigenVector());
    }
}
