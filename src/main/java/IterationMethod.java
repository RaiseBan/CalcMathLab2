public class IterationMethod {


    private static double phi1(double x1, double x2) {
        return 0.3 - 0.1 * Math.pow(x1, 2) - 0.2 * Math.pow(x2, 2);
    }

    public static double getSystem(int sysNumber){
        switch (sysNumber){
            case 1 -> {

            }case 2 -> {

            }case 3 -> {

            }default -> {

            }
        }
    }

    private static double phi2(double x1, double x2) {
        return 0.7 - 0.2 * Math.pow(x1, 2) - 0.1 * x1 * x2;
    }

    private static boolean checkConvergence(int sysNumber, double x1, double x2) {
        // Вычисляем элементы матрицы Якоби
        double j11 = -0.2 * x1;
        double j12 = -0.4 * x2;
        double j21 = -0.4 * x1 - 0.1 * x2;
        double j22 = -0.1 * x1;

        // Вычисляем норму бесконечности матрицы Якоби
        double row1Norm = Math.abs(j11) + Math.abs(j12);
        double row2Norm = Math.abs(j21) + Math.abs(j22);
        double infNorm = Math.max(row1Norm, row2Norm);

        // Проверяем условие сходимости
        return infNorm < 1;
    }

    private static void solveSystem(double x1, double x2, double e, int maxIter) {
        double newX1, newX2;
        double error1, error2;
        int iteration = 0;
        boolean flag = true;

        while (true) {
            newX1 = phi1(x2);
            newX2 = phi2(newX1);

            error1 = Math.abs(newX1 - x1);
            error2 = Math.abs(newX2 - x2);

            x1 = newX1;
            x2 = newX2;
            iteration++;

            System.out.println("Итерация " + iteration + ": x1 = " + x1 + ", x2 = " + x2);
            System.out.println("Погрешности: |x1(k) - x1(k-1)| = " + error1 + ", |x2(k) - x2(k-1)| = " + error2);


            if (Math.max(error1, error2) < e) {
                break;
            }

            if (iteration >= maxIter) {
                System.out.println("Превышено максимальное число итераций, процесс расходится");
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("Решение найдено после " + iteration + " итераций: x1 = " + x1 + ", x2 = " + x2);
            checkSolution(x1, x2);
        }

    }

    private static void checkSolution(double x1, double x2) {
        System.out.println("Вектор невязок: ");
        System.out.println(Math.abs(x1 * x1 + x2 * x2 - 1) + "\n" + Math.abs(x1 * x1 - x2 - 0.5));
    }
}