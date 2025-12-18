import java.util.function.Function;
import static java.lang.Math.*;
import static java.lang.System.out;

public class prov {
    public static void main(String[] args) {
        double[] mas = grad_const_step(2, 1);
        out.printf("Количество итераций: %.0f%n", mas[0]);
        out.printf("Минимальное x: %.10f%n", mas[1]);
        out.printf("Минимальное y: %.10f%n", mas[2]);
        out.printf("Минимальное значение func(x, y): %.10f%n", mas[3]);
    }

    public static double func(double x, double y) {
        return x*x - 3*x*y + 10*y*y + 5*x - 3*y;
    }

    public static double gradx(double x, double y) {
        double h = pow(10, -5);
        return (func(x + h, y) - func(x, y)) / h;
    }

    public static double grady(double x, double y) {
        double h = pow(10, -5);
        return (func(x, y + h) - func(x, y)) / h;
    }

    // Метод дихотомии для поиска оптимального шага alpha
    public static double del_on_two(double x, double y) {
        double eps = 1e-6;
        double delta = eps / 3;

        // Находим разумный интервал [a, b] для alpha
        double a = 0;
        double b = 1.0;  // Начинаем с [0, 1]

        // Проверяем, не лучше ли значение на правой границе
        double f0 = func(x, y);  // значение при alpha = 0
        double grad_x = gradx(x, y);
        double grad_y = grady(x, y);

        // Функция phi(alpha) = f(x - alpha*grad_x, y - alpha*grad_y)
        Function<Double, Double> phi = alpha ->
                func(x - alpha * grad_x, y - alpha * grad_y);

        // Расширяем интервал, если минимум дальше
        double fb = phi.apply(b);
        while (fb < f0 && b < 100) {
            b *= 2;
            fb = phi.apply(b);
        }

        // Если даже при alpha=0 функция не уменьшается, возвращаем 0
        if (phi.apply(delta) >= f0) {
            return 0;
        }

        // Метод дихотомии
        while (abs(b - a) > eps) {
            double x1 = (a + b - delta) / 2;
            double x2 = (a + b + delta) / 2;
            double f1 = phi.apply(x1);
            double f2 = phi.apply(x2);

            if (f1 < f2) {
                b = x2;
            } else {
                a = x1;
            }
        }

        return (a + b) / 2;
    }

    public static double[] grad_const_step(double x_start, double y_start) {
        double eps = 1e-6;
        double x = x_start, y = y_start;
        double f_old = func(x, y);
        double iteration = 0;
        int max_iter = 1000;

        while (iteration < max_iter) {
            iteration += 1;

            // Сохраняем предыдущее значение функции
            double f_prev = f_old;

            // Находим оптимальный шаг методом дихотомии
            double alpha = del_on_two(x, y);

            // Вычисляем градиент в текущей точке
            double gx = gradx(x, y);
            double gy = grady(x, y);

            // Делаем шаг
            double x_new = x - alpha * gx;
            double y_new = y - alpha * gy;
            double f_new = func(x_new, y_new);

            // Переходим в новую точку
            x = x_new;
            y = y_new;
            f_old = f_new;

            // Проверяем условие остановки
            // 1. По изменению функции
            if (abs(f_new - f_prev) < eps) {
                break;
            }

            // 2. По норме градиента (дополнительная проверка)
            double grad_norm = sqrt(gx*gx + gy*gy);
            if (grad_norm < eps) {
                break;
            }

            // Вывод прогресса
            if (iteration % 10 == 0) {
                out.printf("Итерация %.0f: f = %.10f, шаг = %.6f, градиент = %.6f%n",
                        iteration, f_new, alpha, grad_norm);
            }
        }

        return new double[]{iteration, x, y, f_old};
    }
}