import static java.lang.Math.*;
import static java.lang.System.out;

public class Kosatel {
    public static void main(String[] args) {
//        out.println(fi(7)+ fi(11));
        double[] mas = minimise(7, 11);
        out.printf("Мнимальное x на промежутке: %.8f%n", mas[1]);
        out.printf("Mинимальное значение f(x) на промежутке: %.8f%n", mas[2]);
        out.printf("Количество итераций: %.0f%n", mas[0]);
    }

    public static double f(double x) {
        return cos(x) / pow(x, 2);
    }

    public static double fi(double x) {
        double h = pow(10, -5);
        return (f(x + h) - f(x)) / h;
    }

    public static double point_intersection(double a, double b) {
        return (fi(a) * a - fi(b) * b + f(b) - f(a)) / (fi(a) - fi(b));
    }
    public static double[] minimise(double a, double b){
        double eps = pow(10, -6);
        double xm = a;
        double xm1 = point_intersection(a, b);
        double iteration = 1;
        while (abs(fi(xm) * (xm1 - xm)) > eps){
            iteration +=1;
            xm = xm1;
//            double c = fi(xm);
            if(fi(xm) < 0){
                a = xm;
            }
            else{
                b = xm;
            }
            xm1 = point_intersection(a, b);
        }
        double xmin = (a+b)/2;
        return new double[] {iteration, xmin, f(xmin)};
    }
}
