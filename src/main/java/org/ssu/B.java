package org.ssu;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

class PointsChart {

    // конфигурация отображаемого графика (необязательный, нужен для форматирования)
    public static ChartPanel createDemoPanel(XYDataset dataset, String title) {
        // инициируем график
        JFreeChart chart = ChartFactory.createScatterPlot(
                title, "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        // делаем дополнительные настройки (цвет точек, цвет фона, толщину точек и многое другое)
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setBackgroundPaint(Color.WHITE);
        XYItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(0));
        renderer.setSeriesPaint(0, Color.blue);
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setRange(0.00, 1.00);
        domain.setTickUnit(new NumberTickUnit(0.1));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setRange(0.0, 1.0);
        range.setTickUnit(new NumberTickUnit(0.1));

        chart.setTitle(new TextTitle(title, new Font("Roboto", Font.BOLD, 18)));

        return new ChartPanel(chart);
    }
}

public class B extends JFrame {

    // величина К
    private static final int K = 14;
    // количество точек
    private static final double N = Math.pow(2, K);
    // отображаемое имя приложения
    private static final String title = "Option 34. K = " + K + ", (" + (long)N + " points)";

    // выполнения конфигурации графика
    public B(String s) {
        super(s);
        final ChartPanel chartPanel = PointsChart.createDemoPanel(createSampleData(), title);
        this.add(chartPanel, BorderLayout.CENTER);
    }

    // фукнция согласно заданию
    public static long func(long x) {
        return (x ^ 4) ^
                (2 * (x & (1 + 2 * x) &
                        (3 + 4 * x) &
                        (7 + 8 * x) &
                        (15 + 16 * x) &
                        (63 + 64 * x))) ^
                (4 * (x * x + 34));
    }

    // вычисление положения всех точек
    private XYDataset createSampleData() {
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Points");
        double x, y;
        for (int i = 0; i < N; i++) {
            x = (func(i) % N) / N;
            y = (i % N) / N;
            series.add(x, y);
        }
        xySeriesCollection.addSeries(series);
        return xySeriesCollection;
    }

    public static void main(String[] args) {
        // запуск приложения
        EventQueue.invokeLater(() -> {
            B b = new B("Task B");
            b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            b.pack();
            b.setLocationRelativeTo(null);
            b.setVisible(true);
        });
    }
}