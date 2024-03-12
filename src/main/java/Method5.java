public class Method5 {


    public static void runMethod(int funcNumber, double a, double b, double e, int maxIter) {
        double aDirective = Functions.derivative1(funcNumber, a);
        double bDirective = Functions.derivative1(funcNumber, b);
        double lambda;
        double withSign = aDirective;
        double maxx = Math.abs(aDirective);
//        lambda = -1 / Math.max(Math.abs(aDirective), Math.abs(bDirective));
        for (double i = a; i < b; i += 0.1){
            double derictivee = Functions.derivative1(funcNumber, i);
            if (Math.abs(derictivee) >= maxx){
                maxx = Math.abs(derictivee);
                withSign = derictivee;
            }

        }
        lambda = -1/withSign;

        checkCoverage(funcNumber, a, b, lambda);
        Function function = Functions.getFunction(funcNumber);

        int n = 0;
        double x0 = a;
        double x1;

        for (int i = 0; i < maxIter; i++) {
            x1 = Functions.getPhi(funcNumber, x0, lambda);
//            System.out.println("|x1 - x0| = " + Math.abs(x1 - x0));
            if (Math.abs(function.apply(x1)) < e) {
                System.out.println("|x1 - x0| = " + Math.abs(x1 - x0));
                System.out.println("Решение: " + "x* = " + x1 + " f(x*) = " + function.apply(x1) + " Кол-во итераций: " + n);
                break;
            }
            x0 = x1;
            n++;

        }
        System.out.println("X0: " + x0);
        System.out.println(function.apply(x0));
        if (n == maxIter){
            System.out.println("Процесс расходится");
        }


    }

    public static void checkCoverage(int funcNumber, double a, double b, double lambda) {
        if (Math.max(Math.abs(Functions.getDPhi(funcNumber, a, lambda)), Math.abs(Functions.getDPhi(funcNumber, b, lambda))) < 1) {
            System.out.println("Условия сходимости выполняются!");
        } else {
            System.out.println("Условия сходимости не выполняются. Попробуем решить...");
        }
        System.out.println(Functions.getDPhi(funcNumber, a, lambda));
        System.out.println(Functions.getDPhi(funcNumber, b, lambda));
    }
}
