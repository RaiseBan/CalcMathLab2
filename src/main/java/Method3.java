import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method3 {

    public static boolean checkDerivativesSign(int funcNumber, double a, double b){
        boolean hasSameSignAtEndpoints = Math.signum(Functions.derivative1(funcNumber, a)) == Math.signum(Functions.derivative1(funcNumber, b))
                && Math.signum(Math.signum(Functions.derivative2(funcNumber, a))) == Math.signum(Functions.derivative2(funcNumber, b));
        if (!hasSameSignAtEndpoints){
            return false;
        }
        double step = (b - a) / 100; // Примерный шаг для проверки внутри интервала
        for (double x = a; x <= b; x += step) {
            if (Math.signum(Functions.derivative1(funcNumber, x)) != Math.signum(Functions.derivative1(funcNumber, a))
                    || Math.signum(Math.signum(Functions.derivative2(funcNumber, x))) != Math.signum(Functions.derivative2(funcNumber, a))) {
                return false;
            }
        }
        return true;
    }
    public static boolean checkFirstDerivativeNonZero(int funcNumber, double x) {
        return Functions.derivative1(funcNumber, x) != 0;
    }
    public static boolean canApplyNewton(int funcNumber, double a, double b){
        return checkDerivativesSign(funcNumber, a, b) && checkFirstDerivativeNonZero(funcNumber, a) && checkFirstDerivativeNonZero(funcNumber, b);
    }

    public static double chooseInitialApproximation(double a, double b, Function function, int funcNumber){
        if (function.apply(a) * Functions.derivative2(funcNumber, a) > 0){
            return a;
        }else {
            return b;
        }
    }
    public static void runMethod(double initialApproximation, double epsilon, int maxIterations, Function function, int funcNumber){
        double x = initialApproximation;
        List<List<Double>> tableData = new ArrayList<>();
        List<Double> rowData;
        for (int i = 0; i < maxIterations; i++){
            double functionValue = function.apply(x);
            double functionDerivative = Functions.derivative1(funcNumber, x);

            double nextX = x - functionValue/functionDerivative;
            if (Math.abs(nextX - x) <= epsilon ||
                    Math.abs(function.apply(nextX)/Functions.derivative1(funcNumber, nextX)) <= epsilon ||
                    Math.abs(function.apply(nextX)) <= epsilon ){
                break;
            }
            rowData = TablePrinter.addToList(x, functionValue, functionDerivative, nextX, Math.abs(nextX - x));
            tableData.add(rowData);
            x = nextX;
        }

        List<String> headers = new ArrayList<>(Arrays.asList("x_n", "f(x_n)", "f'(x_n)", "x_n+1", "|x_n+1 - x_n|"));
        TablePrinter.printTable(headers, tableData);


    }
}
