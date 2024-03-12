import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method4 {
    public static void runMethod(Function function, double x0, double x1, double e, int maxIterations){
        double f0 = function.apply(x0);
        double f1 = function.apply(x1);
        List<Double> rowData = null;
        List<List<Double>> tableData = new ArrayList<>();
        int n = 0;

        for (int i = 0; i < maxIterations; i++){
            double x2 = x1 - ((x1 - x0)/ (f1 - f0)) * f1;
            double f2 = function.apply(x2);
            n++;
            rowData = TablePrinter.addToList(x0, x1, x2, f2, Math.abs(x2 - x1));
            tableData.add(rowData);
            if (Math.abs(x2 - x1) <= e || Math.abs(function.apply(x2)) <= e){
                break;
            }

            x0 = x1;
            x1 = x2;
            f0 = f1;
            f1 = f2;


        }
        List<String> headers = new ArrayList<>(Arrays.asList("x_{i-1}", "x_{i}", "x_{i+1}", "f(x_{i+1})", "|x_{i+1} - x_{i}|"));
        TablePrinter.printTable(headers, tableData);
        System.out.println("Корень: x= " + rowData.get(2) + " f(x) = "+ rowData.get(3) + " кол-во итераций: " + n);
    }
}
