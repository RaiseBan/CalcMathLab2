import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method1 {
    Function selectedFunction;

    public Method1(Function function) {
        this.selectedFunction = function;
    }

    public void runMethod(double a, double b, double e) {
        List<List<Double>> tableData = new ArrayList<>();
        int n = 0;
        double x;
        double fA;
        double fX;
        double fB;
        while (true) {
            List<Double> rowList;
            x = (a + b) / 2;
            fA = selectedFunction.apply(a);
            fX = selectedFunction.apply(x);
            fB = selectedFunction.apply(b);

            rowList = TablePrinter.addToList(a, b, x, fA, fX, fB, Math.abs(a - b));
            tableData.add(rowList);

            if (fA * fX > 0) {
                a = x;
            } else {
                b = x;
            }
            n++;
            if (Math.abs(a - b) <= e || Math.abs(selectedFunction.apply(x)) < e) {
                break;
            }
        }
        List<String> headers = new ArrayList<>(Arrays.asList("a", "b", "x", "F(a)", "F(b)", "F(x)", "|a-b|"));
        TablePrinter.printTable(headers, tableData);

        // надо вывести x, f(x), n


    }

}
