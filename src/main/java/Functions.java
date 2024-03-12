import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Functions {
    public static Function getFunction(int funcNumber) {
        return x -> {
            switch (funcNumber) {
                case 1 -> {
                    return 5.74 * Math.pow(x, 3) - 2.95 * Math.pow(x, 2) - 10.28 * x + 4.23;
                }
                case 2 -> {
                    return Math.pow(x, 3) - 0.2 * x * x + 0.5 * x + 1.5;
                }
                case 3 -> {
                    return Math.sin(x) + 0.5;
                }
                default -> {
                    return 0;
                }
            }
        };
    }

    public static double getPhi(int funcNumber, double x, double lambda) {
        Function function = getFunction(funcNumber);
        return x + lambda * function.apply(x);
    }

    public static double getDPhi(int funcNumber, double x, double lambda) {
        return 1 + lambda * derivative1(funcNumber, x);
    }

    public static double derivative1(int funcNumber, double x) {
        switch (funcNumber) {
            case 1 -> {
                return 17.22 * Math.pow(x, 2) - 5.9 * x - 10.28;
            }
            case 2 -> {
                return 3 * x * x - 0.4 * x + 0.5;
            }
            case 3 -> {
                return Math.cos(x);
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
            case 2 -> {
                return 6 * x - 0.4;
            }
            case 3 -> {
                return -Math.sin(x);
            }
            default -> {
                return 0;
            }
        }
    }

    public static List<Double> getDefaultSystem(int sysNumber, double x1, double x2) {
        switch (sysNumber) {
            case 1 -> {
                double f1 = 0.1 * Math.pow(x1, 2) + x1 + 0.2 * Math.pow(x2, 2) - 0.3;
                double f2 = 0.2 * Math.pow(x1, 2) + x2 + 0.1 * x1 * x2 - 0.7;
                return new ArrayList<>(Arrays.asList(f1, f2));
            }
            case 2 -> {
                double f1 = x1 * x2 + 2 * x2 * x2 - 12;
                double f2 = x1 * x1 + 4 * x1 * x2 + 20;
                return new ArrayList<>(Arrays.asList(f1, f2));
            }
            case 3 -> {
                double f1 = x1 + Math.cos(x2 - 1) - 0.8;
                double f2 = x2 - Math.cos(x1) - 2;
                return new ArrayList<>(Arrays.asList(f1, f2));
            }
            default -> {
                return null;
            }
        }

    }


}
