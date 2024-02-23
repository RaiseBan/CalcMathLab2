public class Intervals {
    public static boolean hasRoot(double a, double b, Function f){
        return f.apply(a) * f.apply(b) < 0;
    }
}
