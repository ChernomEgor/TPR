import java.util.function.Function;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.System.out;

public class grad_optimal_step {
    public static void main(String[] args) {
        double[] mas = grad_const_step(2, 1);
        out.printf("Количество итераций: %.0f%n", mas[0]);
        out.printf("Минимальное x: %.7f%n", mas[1]);
        out.printf("Минимальное y: %.7f%n", mas[2]);
        out.printf("Mинимальное значение func(x, y): %.7f%n", mas[3]);
    }


    public static double func(double x, double y) {
        return pow(x, 2) - 3 * x * y + 10 * pow(y, 2) + 5 * x - 3 * y;
    }
    public static double gradx(double x, double y) {
        double h = pow(10, -5);
        return (func(x + h, y) - func(x, y)) / h;
    }
    public static double grady(double x, double y) {
        double h = pow(10, -5);
        return (func(x, y + h) - func(x, y)) / h;
    }
    public static double xmin(double x, double y, double alpha) {
        return x - alpha * gradx(x, y);
    }

    public static double ymin(double x, double y, double alpha) {
        return y - alpha * grady(x, y);
    }

    public static double del_on_two(double x, double y, double a, double b) {
        Function<Double, Double> f = alpha ->
                func(xmin(x, y, alpha), ymin(x, y, alpha));
        double eps = pow(10, -6), delta = pow(10, -7);
        while (abs(b - a) > eps) {
            double x1 = (a + b) / 2 - delta;
            double x2 = (a + b) / 2 + delta;
            double f1 = f.apply(x1);
            double f2 = f.apply(x2);
            if (f1 < f2) {
                b = x2;
            } else {
                a = x1;
            }
        }
        return (a + b) / 2;
    }

    public static double[] grad_const_step(double x, double y) {
        double eps = pow(10, -6), alpha = del_on_two(x, y, 0, 1);
        double iteration = 1;
        double xm = xmin(x, y, alpha), ym = ymin(x, y, alpha);
        double first_func = func(x, y);
        while (abs(func(xm, ym) - first_func) > eps) {
            iteration += 1;
            x = xm;
            y = ym;
            first_func = func(x, y);
            alpha = del_on_two(x, y, 0, 1);
            xm = xmin(x, y, alpha);
            ym = ymin(x, y, alpha);
        }
        return new double[]{iteration, xm, ym, func(xm, ym)};
    }


}
