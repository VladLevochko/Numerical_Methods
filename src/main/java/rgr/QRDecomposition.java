package rgr;

/**
 * Created by vlad on 03.05.16.
 */
public class QRDecomposition {
    private Matrix Q;
    private Matrix R;

    public QRDecomposition(Matrix q, Matrix r) {
        this.Q = q;
        this.R = r;
    }

    public Matrix getQ() {
        return Q;
    }

    public Matrix getR() {
        return R;
    }

    public Matrix getQR() {
        Matrix result = null;
        try {
            result = Q.multiply(R);
        } catch (MatrixException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Matrix getRQ() {
        Matrix result = null;
        try {
            result = R.multiply(Q);
        } catch (MatrixException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void print() {
        System.out.println("Matrix Q");
        Q.print();
        System.out.println("Matrix R");
        R.print();
    }
}
