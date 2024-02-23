public class Functions {
    public static Function getFunction(int funcNumber) {
        return x -> {
            switch (funcNumber) {
                case 1 -> {
                    return 5.74 * Math.pow(x, 3) - 2.95 * Math.pow(x, 2) - 10.28 * x + 4.23;
                }
                default -> {
                    return 0;
                }
            }
        };
    }

//    public static double derivative(Function f, double x, int order, double h){
//        if (order == 0){
//            return f.apply(x);
//        } else if (order == 1) {
//            return (f.apply(x+h) - f.apply(x - h)) / (2*h);
//        }else {
//            return (derivative(f, x + h , order - 1, h) - derivative(f, x - h, order - 1, h)) / (2 * h);
//        }
//    }

    public static double derivative1(int funcNumber, double x) {
        switch (funcNumber) {
            case 1 -> {
                return 17.22 * Math.pow(x, 2) - 5.9 * x - 10.28;
            }
            default -> {
                return 0;
            }
        }
    }
    public static double derivative2(int funcNumber, double x) {
        switch (funcNumber) {
            case 1 -> {
                return 34.44 * x - 5.9;
            }
            default -> {
                return 0;
            }
        }
    }

}
