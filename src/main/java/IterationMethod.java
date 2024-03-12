import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IterationMethod {

    public static List<Double> getSystemJacob(int sysNumber, double x1, double x2) {
        switch (sysNumber) {
            case 1 -> {
                return new ArrayList<>(Arrays.asList(-0.2 * x1, -0.4 * x2, -0.4 * x1 - 0.1 * x2, -0.1 * x1));
            }
            case 2 -> {
                return new ArrayList<>(Arrays.asList(0.0, -4 - ((12 - 2 * x2 * x2) / x2 * x2), (-3 / 4) * x1 * x1 - 5, 0.0));
            }
            case 3 -> {
                return new ArrayList<>(Arrays.asList(0.0, Math.sin(x2 - 1), -Math.sin(x1), 0.0));
            }
            default -> {
                return null;
            }
        }
    }

    public static List<Double> getSystem(int sysNumber, double x1, double x2) {
        switch (sysNumber) {
            case 1 -> {
                double phi1 = 0.3 - 0.1 * Math.pow(x1, 2) - 0.2 * Math.pow(x2, 2);
                double phi2 = 0.7 - 0.2 * Math.pow(x1, 2) - 0.1 * x1 * x2;
                return new ArrayList<>(Arrays.asList(phi1, phi2));
            }
            case 2 -> {
                double phi1 = (12 - 2 * x2 * x2) / x2;
                double phi2 = (-20 - x1 * x1) / 4 * x1;
                return new ArrayList<>(Arrays.asList(phi1, phi2));
            }case 3 -> {
                double phi1 = 0.8 - Math.cos(x2-1);
                double phi2 = Math.cos(x1) + 2;
                return new ArrayList<>(Arrays.asList(phi1, phi2));
            }
            default -> {
                return null;
            }
        }
    }


    private static boolean checkConvergence(List<Double> jacob) {
        double row1Norm = Math.abs(jacob.get(0)) + Math.abs(jacob.get(1));
        System.out.println(jacob.get(0) + " " + jacob.get(1));
//        System.out.println(Math.abs(jacob.get(0))+ " " + Math.abs(jacob.get(1)));
//        System.out.println(row1Norm);
        double row2Norm = Math.abs(jacob.get(2)) + Math.abs(jacob.get(3));
        System.out.println(jacob.get(2) + " " + jacob.get(3));
        double infNorm = Math.max(row1Norm, row2Norm);
//        System.out.println(Math.abs(jacob.get(2))+ " " + Math.abs(jacob.get(3)));
//        System.out.println(row2Norm);
        // Проверяем условие сходимости
        return infNorm < 1;
    }

    public static void solveSystem(int systemNumber, double x1, double x2, double e, int maxIter) {


        if (!checkConvergence(getSystemJacob(systemNumber, x1, x2))) {
            System.err.println("Условие сходимости не достигнуто! Пробуем решить...");
        } else {
            System.out.println("Условие сходимости достингуто!");
        }


        double newX1, newX2;
        double error1, error2;
        int iteration = 0;
        boolean flag = true;

        while (true) {
            List<Double> eqs = getSystem(systemNumber, x1, x2);

            newX1 = eqs.get(0);
            newX2 = eqs.get(1);

            error1 = Math.abs(newX1 - x1);
            error2 = Math.abs(newX2 - x2);

            x1 = newX1;
            x2 = newX2;
            List<Double> funcc = Functions.getDefaultSystem(systemNumber, x1, x2);
            iteration++;

//            System.out.println("Итерация " + iteration + ": x1 = " + x1 + ", x2 = " + x2);
//            System.out.println("Погрешности: |x1(k) - x1(k-1)| = " + error1 + ", |x2(k) - x2(k-1)| = " + error2);


            if (Math.abs(funcc.get(0)) < e && Math.abs(funcc.get(1)) < e ) {
                System.out.println(x1 + " " + x2 +  " " + funcc.get(0) + " " + funcc.get(1));
                System.out.println("Итеации " + iteration);
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
            checkSolution(systemNumber, x1, x2);
        }

    }

    private static void checkSolution(int systemNumber, double x1, double x2) {
        System.out.println("Проверка решения при найденных x1, x2: ");
        System.out.printf("e = %.10f\n", Math.abs(getSystem(systemNumber, x1, x2).get(0) - x1));
        System.out.printf("e = %.10f\n", Math.abs(getSystem(systemNumber, x1, x2).get(1) - x2));
    }
}