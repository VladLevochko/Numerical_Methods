package additional_task_lab12;

/**
 * Created by vlad on 19.04.16.
 */
public class Function {

    public double value(double t, double y) {
        return 10 * Math.pow(t, 2) * y * Math.exp(-2 * t);
    }

    public double derivative(double t) {
        return 10 * Math.pow(t, 2) * Math.exp(-2 * t);
    }
}