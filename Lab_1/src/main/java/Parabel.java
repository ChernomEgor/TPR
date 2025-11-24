import static java.lang.Math.*;
import static java.lang.System.out;

public class Parabel {
    public static void main(String[] args) {
//        out.println(fi(7)+ fi(11));
        double[] mas = minimise(7, 11);
        out.printf("Мнимальное x на промежутке: %f%n", mas[1]);
        out.printf("Mинимальное значение f(x) на промежутке: %f%n", mas[2]);
        out.printf("Количество итераций: %.0f%n", mas[0]);
    }
    public static double f(double x) {
        return cos(x) / pow(x, 2);
    }
    public static double fii(double x) {
        double h = pow(10, -5);
        return (fi(x + h) - fi(x)) / h;
    }
    public static double fi(double x) {
        double h = pow(10, -5);
        return (f(x + h) - f(x)) / h;
    }
    public static double minx(double x){
        return x-(fi(x) / fii(x));
    }
    public static double[] minimise(double a, double b){
        double eps = pow(10, -6);
        double xm = 9;
        double xm1 = minx(xm);
        double iteration = 1;
        while (abs(f(xm)-f(xm1))>eps){
            iteration+=1;
            xm = xm1;
            xm1 = minx(xm);
            if((xm1>b) || (xm1<a)){
                xm1 = (a+b)/2;
            }
        }

        double xmin = xm1;
        return new double[] {iteration, xmin, f(xmin)};
    }


}
