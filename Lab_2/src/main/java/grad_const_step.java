import static java.lang.System.out;
import static java.lang.Math.*;

public class grad_const_step {
    public static void main(String[] args) {
        double[] mas = method_grad_const_step(2, 1);
        out.printf("Количество итераций: %.0f%n", mas[0]);
        out.printf("Мнимальное x: %.7f%n", mas[1]);
        out.printf("Мнимальное y: %.7f%n", mas[2]);
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


    public static double[] method_grad_const_step(double x, double y) {
        double eps = pow(10, -6), alpha = pow(10,-1);
        double iteration = 1;
        double xm = xmin(x, y, alpha), ym = ymin(x, y, alpha);
        double first_func = func(x, y);
        while (abs(first_func - func(xm, ym)) > eps) {
            iteration += 1;
            if (func(xm, ym) >= first_func) {
                alpha = alpha / 2;
            }
            x = xm;
            y = ym;
            first_func = func(x, y);
            xm = xmin(x, y, alpha);
            ym = ymin(x, y, alpha);
        }
        return new double[]{iteration, xm, ym, func(xm, ym)};
    }


}
