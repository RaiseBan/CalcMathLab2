import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.Layer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.BasicStroke;
import java.io.IOException;


public class GraphPlotter {


    private static void displayChart(JFreeChart chart) {
        // Создаем панель с графиком
        ChartPanel panel = new ChartPanel(chart);

        // Создаем окно для отображения графика
        JFrame frame = new JFrame("График уравнения");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel); // Добавляем панель с графиком в контент-панель окна
        frame.pack(); // Упаковываем окно
        frame.setLocationRelativeTo(null); // Центрируем окно
        frame.setVisible(true); // Делаем окно видимым
    }

    private static void rawDraw(XYSeriesCollection dataset, boolean fileFlag) {
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
        domainAxis.setTickUnit(new NumberTickUnit(0.5));
        domainAxis.setRange(-10.0, 10.0);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setTickUnit(new NumberTickUnit(0.5));
        rangeAxis.setRange(-10.0, 10.0);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        BasicStroke wideLine = new BasicStroke(3.0f); // Устанавливаем толщину линии
        renderer.setSeriesStroke(0, wideLine); // Применяем толщину линии для серии данных

        plot.setRenderer(renderer);

        // Настройка размера изображения
        int width = 1920; /* Ширина изображения */
        int height = 1080; /* Высота изображения */

        if (fileFlag){
            try {
                ChartUtils.saveChartAsJPEG(new File("Chart.jpeg"), chart, width, height);
            } catch (IOException e) {
                System.out.println("Произошла ошибка при сохранении графика: " + e.getMessage());
            }
        } else {
            System.out.println("выводим график:");
            displayChart(chart);
        }
    }

    public static void draw(double a, double b, Function function, boolean fileFlag) {
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

        rawDraw(dataset, fileFlag);
    }

    public static void drawSys(int sysNumber, boolean fileFlag) {
        XYSeries series = new XYSeries("Equation");
        XYSeries series2 = new XYSeries("Equation 2");
        double minX = -10;
        double maxX = 10;
        double minY = -10;
        double maxY = 10;
        double step;
        if (sysNumber == 1 || sysNumber == 3){
            step = 0.1;
        }else{
            step = 0.1;
        }

        for (double x1 = minX; x1 <= maxX; x1 += 0.005) {
            for (double x2 = minY; x2 <= maxY; x2 += 0.005) {
                double result = Functions.getDefaultSystem(sysNumber, x1, x2).get(0);
                double result2 = Functions.getDefaultSystem(sysNumber, x1, x2).get(1);

                if (Math.abs(result2) < step){
                    series2.add(x1, x2);
                }
                if (Math.abs(result) < step) {
                    series.add(x1, x2);
                }
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();


        dataset.addSeries(series);
        dataset.addSeries(series2);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Equation Plot",
                "x1",
                "x2",
                dataset
        );

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
//
//        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setDomainPannable(true);
//        plot.setRangePannable(true);
//
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-1, -1, 2, 2));
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-1, -1, 2, 2));
//
//
//        plot.setDomainZeroBaselineVisible(true);
//        plot.setRangeZeroBaselineVisible(true);
//        plot.setDomainZeroBaselinePaint(java.awt.Color.BLACK);
//        plot.setRangeZeroBaselinePaint(java.awt.Color.BLACK);

        if (fileFlag){
            try {
                ChartUtils.saveChartAsPNG(new File("equation_plot.png"), chart, width, height);
                System.out.println("График сохранен в файл equation_plot.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            displayChart(chart);
        }

    }

}
