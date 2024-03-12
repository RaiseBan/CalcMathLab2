import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);

        double a = 0, b = 0, e = 0;
        int methodNumber = 3;
        int funcNumber = 1;
        boolean file_flag = false;
        int maxIter = 10;
        Function function;
        System.out.println("""
                Формат входного файла:
                1. Номер функции
                2. Коэффициент а
                3. Коэффициент b
                4. Точность e
                5. Метод решения: 1/3/4
                6. Кол-во итераций (для методов 3, 4)
                7. Вывод результата: в терминал - 1; в файл - 2
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
                    System.out.println("1) 5.74x^3 - 2.95x^2 - 10.28x + 4.23");
                    System.out.println("2) x^3 - 0.2x^2 + 0.5x + 1.5");
                    System.out.println("3) sin(x) + 0.5");
                    try {
                        funcNumber = Integer.parseInt(console.next().trim());
                        function = Functions.getFunction(funcNumber);
                        if (funcNumber == 1){
                            GraphPlotter.draw(-4, 4, function, false);
                        }
                        if (funcNumber == 2){
                            GraphPlotter.draw(-8, 8, function, false);
                        }
                        if (funcNumber == 3){
                            GraphPlotter.draw(-4, 4, function, false);
                        }
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
                while (true) {
                    System.out.println("Введите a: ");
                    try {
                        a = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
                while (true) {
                    System.out.println("Введите b: ");
                    try {
                        b = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
                while (true) {
                    System.out.println("Введите точность e:");
                    try {
                        e = Double.parseDouble(console.next().trim());
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
                while (true) {
                    System.out.println("Введите номер метода:\n2 - метод хорд\n3 - метод Ньютона\n4 - метод секущих\n5 - метод простых итераций");
                    try {
                        methodNumber = Integer.parseInt(console.next().trim()); //TODO: доделать
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
                while (true) {
                    try {
                        System.out.println("Вывод результата: в терминал - false; в файл - true");
                        file_flag = Boolean.parseBoolean(console.next().trim());
                        break;
                    } catch (NumberFormatException exception) {
                        System.out.println("Попробуйте еще ... раз");
                    }
                }
            } else {

                FileReader fileReader = new FileReader("src/main/test.txt");
                Scanner scanner = new Scanner(fileReader);
                try {
                    funcNumber = Integer.parseInt(scanner.nextLine().trim());
                    a = Double.parseDouble(scanner.nextLine().trim());
                    b = Double.parseDouble(scanner.nextLine().trim());
                    e = Double.parseDouble(scanner.nextLine().trim());
                    methodNumber = Integer.parseInt(scanner.nextLine().trim());
                    try {
                        maxIter = Integer.parseInt(scanner.next().trim());
                    } catch (NumberFormatException exception) {
                        if (methodNumber == 3 || methodNumber == 4) {
                            System.err.println("Неверные данные в файле. Неверно указано кол-во итераций");
                            System.exit(1);
                        }
                    }

                    file_flag = Boolean.parseBoolean(scanner.next().trim());

                }catch (Exception exception){
                    System.err.println("неверные данные в файле. Попробуйте еще ... раз");
                    System.exit(1);
                }


            }
            TablePrinter.fileFlag = file_flag;
            function = Functions.getFunction(funcNumber);
            if (Intervals.hasRoot(a, b, function)) {
                if (Intervals.checkRootsOnInterval(a, b, function)) {
                    switch (methodNumber) {
                        case 1 -> {
                            if (Intervals.hasRoot(a, b, function)) {
                                Method1 method1 = new Method1(function);
                                method1.runMethod(a, b, e);
                            }
                        }
                        case 3 -> {
                            if (!file_flag) {
                                System.out.println("Введите кол-во итераций:");
                                maxIter = Integer.parseInt(console.next().trim());
                            }

                            if (Method3.canApplyNewton(funcNumber, a, b)) {
                                Method3.runMethod(Method3.chooseInitialApproximation(a, b, function, funcNumber), e, maxIter, function, funcNumber);
                            } else {
                                System.err.println("Не выполняются условия сходимости!");
                                System.out.println("Попробуем решить...");
                                Method3.runMethod(Method3.chooseInitialApproximation(a, b, function, funcNumber), e, maxIter, function, funcNumber);

                            }
                        }
                        case 4 -> {
                            if (!file_flag) {
                                System.out.println("Введите кол-во итераций:");
                                maxIter = Integer.parseInt(console.next().trim());
                            }
                            if (Method3.canApplyNewton(funcNumber, a,b)){
                                System.out.println("Условия сходимости выполнены");
                            }else {
                                System.out.println("Условие сходимости не выполняется!\nпопробуем решить...");
                            }
                            double x0 = Method3.chooseInitialApproximation(a, b, function, funcNumber);
                            if (x0 == a) {
                                Method4.runMethod(function, x0, x0 + e, e, maxIter);
                            } else {
                                Method4.runMethod(function, x0 - e, x0, e, maxIter);
                            }

                        }
                        case 5 -> {
                            if (!file_flag) {
                                System.out.println("Введите кол-во итераций:");
                                maxIter = Integer.parseInt(console.next().trim());
                            }

                            Method5.runMethod(funcNumber, a, b, e, maxIter);
                        }
                    }
                    GraphPlotter.draw(a, b, function, true);

                } else {
                    System.out.println("На заданном интервале больше 1 корня!");
                }
            } else {
                System.out.println("На заданном интервале нет корней!");
            }
        } else {
            System.out.println("Предлагаемые системы:");
            System.out.println("-------1-------");
            System.out.println("/");
            System.out.println("|" + "0.1*x1^2 + x1 + 0.2*x^2 - 0.3 = 0");
            System.out.println("|" + "0.2*x1^2 + x2 + 0.1*x1*x2 - 0.7 = 0");
            System.out.println("\\");
            System.out.println("-------1-------");
            System.out.println("|");
            System.out.println("-------2-------");
            System.out.println("/");
            System.out.println("|" + "x1 * x2 + 2 * x2^2 - 12 = 0");
            System.out.println("|" + "0.2*x1^2 + x2 + 0.1*x1*x2 - 0.7 = 0");
            System.out.println("\\");
            System.out.println("-------2-------");
            System.out.println("-------3-------");
            System.out.println("/");
            System.out.println("|" + "x1 + cos(x2 - 1) = 0.8");
            System.out.println("|" + "x2 - cos(x1) = 2");
            System.out.println("\\");
            System.out.println("-------3-------");
            System.out.println("Введите номер системы: ");
            int sysNumber = Integer.parseInt(console.next().trim());
            GraphPlotter.drawSys(sysNumber, false);
            System.out.println("Начальное приближение: x1");
            double x1 = Double.parseDouble(console.next().trim());
            System.out.println("Начальное приближение: x2");
            double x2 = Double.parseDouble(console.next().trim());
            System.out.println("Точность: ");
            e = Double.parseDouble(console.next().trim());
            System.out.println("Максимальное кол-во итераций: ");
            maxIter = Integer.parseInt(console.next().trim());

            IterationMethod.solveSystem(sysNumber, x1, x2, e, maxIter);
        }


    }
}
