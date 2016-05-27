package rgr;

/**
 * Created by vlad on 03.05.16.
 */
public class IdentityMatrix extends Matrix {

    public IdentityMatrix(int n) {
        super(n, n);
        this.A = new double[n][n];
        for (int i = 0; i < n; i++) {
            A[i][i] = 1;
        }
    }

}
