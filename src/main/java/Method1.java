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
        List<Double> rowList = null;
        while (true) {


            fA = selectedFunction.apply(a);
            fB = selectedFunction.apply(b);
            x = (a*fB - b*fA) / (fB - fA);
            fX = selectedFunction.apply(x);
            System.out.println("x: " + x);

            rowList = TablePrinter.addToList(a, b, x, fA, fX, fB, Math.abs(a - b));
            tableData.add(rowList);

            if (fA * fX > 0) {
                a = x;
            } else {
                b = x;
            }
            n++;
            if (Math.abs(a - b) <= e || Math.abs(selectedFunction.apply(x)) <= e) {
                break;
            }
        }
//        List<String> headers = new ArrayList<>(Arrays.asList("a", "b", "x", "F(a)", "F(b)", "F(x)", "|a-b|"));
//        TablePrinter.printTable(headers, tableData);
        System.out.println("x: " + rowList.get(2) + " f(x)= " + rowList.get(4) + " кол-во итераций: " + n);

    }

}
