import static java.lang.System.out;
import static java.lang.Math.*;

public class del_to_two {
    public static void main(String[] args) {
        double[] mas = del_on_two(7, 11);
        out.printf("Мнимальное x на промежутке: %.8f%n", mas[1]);
        out.printf("Mинимальное значение f(x) на промежутке: %.8f%n", mas[2]);
        out.printf("Количество итераций: %.0f%n", mas[0]);
    }


    public static double f(double x) {
        return cos(x) / pow(x, 2);
    }

    public static double[] del_on_two(double a, double b) {
        double eps = pow(10, -6), delta = pow(10, -7);
        double iteration = 1;
        while (abs(b - a) > eps) {
            iteration += 1;
            double x1 = (a + b) / 2 - delta;
            double x2 = (a + b) / 2 + delta;
            double f1 = f(x1);
            double f2 = f(x2);
            if (f1 < f2) {
                b = x2;
            } else {
                a = x1;
            }
        }
        double xmin = (a + b) / 2;
        return new double[] {iteration, xmin, f(xmin)};
    }
}
