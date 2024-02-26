public class Intervals {
    public static boolean hasRoot(double a, double b, Function f){
        return f.apply(a) * f.apply(b) < 0;
    }
    public static boolean checkRootsOnInterval(double a, double b, Function function){
        int roots = 0;
        for (double i = a; i <= b; i += 0.1){
            if ( (function.apply(i) > 0 && function.apply(i+0.1) < 0) ||
                    (function.apply(i) < 0 && function.apply(i + 0.1) > 0) ){
                roots++;
            }
        }
        if (roots == 1){
            return true;
        }else{
            return false;
        }
    }
}
