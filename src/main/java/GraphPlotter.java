import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.Layer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphPlotter {

    private static void rawDraw(XYSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "f(x)",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = chart.getXYPlot();

        // Добавление оси X
        ValueMarker xAxis = new ValueMarker(0, Color.BLACK, new BasicStroke(1.0f));
        plot.addDomainMarker(xAxis, Layer.FOREGROUND);

        // Добавление оси Y
        ValueMarker yAxis = new ValueMarker(0, Color.BLACK, new BasicStroke(1.0f));
        plot.addRangeMarker(yAxis, Layer.FOREGROUND);

        // Настройка внешнего вида осей
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);

        // Настройка размера изображения
        int width = 1920; /* Ширина изображения */
        int height = 1080; /* Высота изображения */

        // Сохранение графика в файл
        try {
            ChartUtils.saveChartAsJPEG(new File("Chart.jpeg"), chart, width, height);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при сохранении графика: " + e.getMessage());
        }
    }

    public static void draw(double a, double b, Function function) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        a = a - 1; // Расширение интервала для "запаса"
        b = b + 1; // Расширение интервала для "запаса"

        XYSeries series = new XYSeries("Function");

        // Заполнение данных функции
        double len = b - a;
        for (double i = a; i <= b; i += len / 100) { // Увеличиваем количество точек для гладкости линии
            series.add(i, function.apply(i));
        }

        dataset.addSeries(series);

        // Добавление осей в набор данных
        XYSeries xAxisSeries = new XYSeries("X-Axis");
        XYSeries yAxisSeries = new XYSeries("Y-Axis");
        xAxisSeries.add(a, 0);
        xAxisSeries.add(b, 0);
        yAxisSeries.add(0, function.apply(a));
        yAxisSeries.add(0, function.apply(b));
        dataset.addSeries(xAxisSeries);
        dataset.addSeries(yAxisSeries);

        rawDraw(dataset);
    }
}
