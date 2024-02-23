import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);

        double a, b, e;
        int methodNumber = 3;
        int funcNumber = 1;
        boolean file_flag;

        System.out.println("""
                Формат входного файла:
                1. Номер выбранной функции: 1-3
                2. Коэффициент а
                3. Коэффициент b
                4. Точность e
                5. Метод решения: 2/4/5
                6. Вывод результата в файл/консоль: true/false
                """);
        System.out.println("Введите: 1 - Решение уравнение; 2 - решение системы");
        int answer = Integer.parseInt(console.next().trim());
        if (answer == 1) {
            System.out.println("Введите: 1 - ввод с консоли; 2 - чтения с файла");
            int num = console.nextInt();

            while (!(num == 1 || num == 2)) {
                System.err.println("Ошибка ввода");
                System.out.println("Введите: 1 - ввод с консоли; 2 - чтения с файла");
                num = console.nextInt();
            }
            if (num == 1) {
                while (true) {
                    System.out.println("Введите номер исследуемой функции (1-3): ");
                    try {
                        funcNumber = Integer.parseInt(console.next().trim());
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите a: ");
                    try {
                        a = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите b: ");
                    try {
                        b = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите точность e:");
                    try {
                        e = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите номер метода (1, 3, 4):");
                    try {
                        methodNumber = Integer.parseInt(console.next().trim()); //TODO: доделать
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
            } else {
                FileReader fr = new FileReader("src/main/res/input.txt");
                Scanner scan = new Scanner(fr);

                funcNumber = Integer.parseInt(scan.nextLine().trim());
                a = Double.parseDouble(scan.nextLine().trim());
                b = Double.parseDouble(scan.nextLine().trim());
                e = Double.parseDouble(scan.nextLine().trim());

                methodNumber = Integer.parseInt(scan.nextLine().trim());
            }

            Function function = Functions.getFunction(funcNumber);

            switch (methodNumber) {
                case 1 -> {
                    if (Intervals.hasRoot(a, b, function)) {
                        Method1 method1 = new Method1(function);
                        method1.runMethod(a, b, e);
                    } else {
                        System.err.println("На заданном интервале нет корней!");
                    }
                }
                case 3 -> {
                    System.out.println("Введите кол-во итераций:");
                    int maxIter = Integer.parseInt(console.next().trim());
                    if (Method3.canApplyNewton(funcNumber, a, b) && Intervals.hasRoot(a, b, function)) {
                        Method3.runMethod(Method3.chooseInitialApproximation(a, b, function, funcNumber), e, maxIter, function, funcNumber);
                    } else {
                        System.err.println("Не выполняются условия сходимости! Попробуйте уменьшить интервал");
                    }
                }
                case 4 -> {
                    System.out.println("Введите кол-во итераций:");
                    int maxIter = Integer.parseInt(console.next().trim());
                    if (Intervals.hasRoot(a, b, function)) {
                        Method4.runMethod(function, a, b, e, maxIter);
                    }

                }
            }
            GraphPlotter.draw(a, b, function);
        }else {
            
        }


    }
}
