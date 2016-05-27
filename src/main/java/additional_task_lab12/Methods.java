package additional_task_lab12;

import java.util.ArrayList;

public class Methods {


    public ArrayList<Double> rungeKutta(Function f, double m, double t0, double tm, int N) {

        double h = (tm - t0) / m;
        ArrayList<Double> ans = new ArrayList<>();
        ans.add(1.0);

        double t = t0, y = 1;
        for (int i = 1; i < N; i++) {
            double k1 = f.value(t, y);
            double k2 = f.value(t + 0.5 * h, y + 0.5 * h * k1);
            double k3 = f.value(t + 0.5 * h, y + 0.5 * h * k2);
            double k4 = f.value(t + h, y + h * k3);
            double dy = h * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
            t += h;
            y += dy;
            ans.add(y);
        }

        return ans;
    }

    public ArrayList<Double> brm3(Function f, double m, double t0, double tm) {

        int N = 3;
        double h = (tm - t0) / m;
        ArrayList<Integer> bp = new ArrayList<>();
        bp.add(9); bp.add(18); bp.add(3);

        ArrayList<Double> ans = rungeKutta(f, m, t0, tm, N);

        for (int i = 2; i < m; i++) {
            double sum = 0;
            for (int j = 0; j < N; j++)
                sum += bp.get(j) * f.value(t0 + h * (i - j), ans.get(i - j));
            ans.add(i + 1, 10 * ans.get(i - 2) + 9 * ans.get(i - 1) - 18 * ans.get(i) + h * sum);
        }

        return ans;
    }

    public ArrayList<Double> gere3(Function f, double m, double t0, double tm) {

        double h = (tm - t0) / m;
        ArrayList<Double> ans = rungeKutta(f, m, t0, tm, 3);

        double cur;
        for (int i = 2; i < m; i++) {
            double up = 18. / 11. * ans.get(i) - 9. / 11. * ans.get(i - 1) + 2. / 11. * ans.get(i - 2);
            double down = 1. - 6. / 11. * h * f.derivative(t0 + i * h);
            cur = up / down;
            ans.add(cur);
        }

        return ans;
    }

    public ArrayList<Double> gere4(Function f, double m, double t0, double tm) {

        double h = (tm - t0) / m;
        ArrayList<Double> ans = rungeKutta(f, m, t0, tm, 4);

        double cur;
        for (int i = 3; i < m; i++) {
            double up = 48. / 25. * ans.get(i) - 36. / 25. * ans.get(i - 1) + 16. / 25. * ans.get(i - 2) - 3. / 25. * ans.get(i - 3);
            double down = 1. - 12. / 25. * h * f.derivative(t0 + i * h);
            cur = up / down;
            ans.add(cur);
        }

        return ans;
    }



    public static void main(String args[]) {
        Methods m = new Methods();
        double M = 10, t0 = 0, tm = 1;
        double h = (tm  - t0) / M;
        ArrayList<Double> ansBrm3 = m.brm3(new Function(), M, t0, tm);
        ArrayList<Double> ansRungeKutta = m.rungeKutta(new Function(), M, t0, tm, 11);
        ArrayList<Double> ansGere3 = m.gere3(new Function(), M, t0, tm);
        ArrayList<Double> ansGere4 = m.gere4(new Function(), M, t0, tm);

        System.out.println("i" + "\t" + "h" + "\t\t" + "brm 3" + "\t\t\t\trunge-kutta 3 \t\tgere 3 \t\t\t\tgere 4");
        for (int i = 0; i < M + 1; i++) {
            System.out.println(i + "\t" + String.format("%.2f", (t0 + h * i))  + "\t" + ansBrm3.get(i) + "\t" + ansRungeKutta.get(i) + "\t" + ansGere3.get(i) + "\t" + ansGere4.get(i));
        }
    }
}
