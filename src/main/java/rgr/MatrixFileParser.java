package rgr;

import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by vlad on 15.05.16.
 */
public class MatrixFileParser {

    public static void main(String args[]) {
        String s  = "/home/vlad/IdeaProjects/Numerical_Methods/src/main/java/matrices/sparse_27_27_ex5.tx";

        int c = 12;
//        Scanner ins = new Scanner(System.in);
//        s = ins.nextLine();
        try {
            Scanner in = new Scanner(new File(s));
            PrintWriter out = new PrintWriter(new File(s + "t"));

            int n = in.nextInt();
            int m = in.nextInt();
            ArrayList<ArrayList<Double>> a = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                a.add(new ArrayList<>());
            }
            int count = m / c;
            if (m % c != 0)
                count++;
            for (int k = 0; k < count; k++) {
                for (int i = 0; i < 4; i++) {
                    in.nextLine();
                }

                int l = c;
                if (k == count - 1 && count != n / c)
                    l = m % c;
                for (int i = 0; i < n; i++) {
                    ArrayList<Double> t = a.get(i);
                    for (int j = 0; j < l; j++) {
                        double d = in.nextDouble();
                        t.add(d);
                    }
                }

            }

            DecimalFormat format = new DecimalFormat();
            format.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            format.setMinimumIntegerDigits(1);
            format.setMaximumFractionDigits(4);
            format.setMinimumFractionDigits(4);
            format.setGroupingUsed(false);
            for (int i = 0; i < n; i++) {
                ArrayList<Double> t = a.get(i);
                for (int j = 0; j < m; j++) {
                    String d = format.format(t.get(j));
                    out.print(d + " ");
                }
                out.println();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
