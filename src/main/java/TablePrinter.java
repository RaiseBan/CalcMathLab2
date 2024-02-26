import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TablePrinter {
    public static boolean fileFlag = false;
    public static List<Double> addToList(double... values) {
        List<Double> collection = new ArrayList<>();
        for (double value : values) {
            collection.add(value);
        }

        return collection;
    }
    public static void printTable(List<String> headers, List<List<Double>> tableData) {
        StringBuilder resultTable = new StringBuilder();

        // Вычисляем максимальную ширину каждого столбца
        List<Integer> columnWidths = headers.stream()
                .map(header -> header.length())
                .collect(Collectors.toList());

        for (List<Double> row : tableData) {
            for (int i = 0; i < row.size(); i++) {
                columnWidths.set(i, Math.max(columnWidths.get(i), String.format("%.5f", row.get(i)).length()));
            }
        }

        // Добавляем столбец для номера итерации
        headers.add(0, "№ итерации");
        columnWidths.add(0, headers.get(0).length());

        // Создаем формат строки для печати
        String format = headers.stream()
                .map(header -> "| %-" + columnWidths.get(headers.indexOf(header)) + "s ")
                .collect(Collectors.joining()) + "|\n";

        // Создаем разделитель между строками таблицы
        String separator = headers.stream()
                .map(header -> "+-" + "-".repeat(columnWidths.get(headers.indexOf(header))) + "-")
                .collect(Collectors.joining()) + "+\n";

        // Печатаем разделитель, заголовки и разделитель после заголовков
        resultTable.append(separator);
        resultTable.append(String.format(format, headers.toArray()));
        resultTable.append(separator);

        // Печатаем данные таблицы
        for (int i = 0; i < tableData.size(); i++) {
            List<Double> rowData = tableData.get(i);
            // Форматируем номер итерации и добавляем его к данным
            List<String> formattedRow = Stream.concat(
                            Stream.of(String.format("%-" + columnWidths.get(0) + "d", i + 1)),
                            rowData.stream().map(d -> String.format("%.5f", d)))
                    .collect(Collectors.toList());
            resultTable.append(String.format(format, formattedRow.toArray()));
            resultTable.append(separator);
        }

        // Печать в консоль или запись в файл в зависимости от флага
        if (fileFlag) {
            try (FileWriter writer = new FileWriter("output.txt")) {
                writer.write(resultTable.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print(resultTable);
        }
    }

}
