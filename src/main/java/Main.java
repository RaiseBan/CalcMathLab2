import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        double a, b, e;
        int number;
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

        System.out.println("Введите: 1 - ввод с консоли; 2 - чтения с файла");
        int num = console.nextInt();

        while (!(num == 1 || num == 2)) {
            System.err.println("Ошибка ввода");
            System.out.println("Введите: 1 - ввод с консоли; 2 - чтения с файла");
            num = console.nextInt();
        }
        switch (num){
            case 1 -> {
                while (true) {
                    System.out.println("Введите номер исследуемой функции (1-3): ");
                    try {
                        number = Integer.parseInt(console.next().trim().replaceAll(",", "\\."));
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите a: ");
                    try {
                        a = Double.parseDouble(console.next().trim().replaceAll(",", "\\."));
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите b: ");
                    try {
                        b = Double.parseDouble(console.next().trim().replaceAll(",", "\\."));
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
                while (true) {
                    System.out.println("Введите точность e:");
                    try {
                        e = Double.parseDouble(console.next().trim().replaceAll(",", "\\."));
                        break;
                    } catch (NumberFormatException ignored) {
                        System.out.println("Повторите ввод");
                    }
                }
            }
        }
        // начало выполнения методов:

        



    }
}
