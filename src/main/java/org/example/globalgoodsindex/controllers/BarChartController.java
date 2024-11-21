package org.example.globalgoodsindex.controllers;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.example.globalgoodsindex.Main;


public class BarChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize() {
        populateChart();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(5000), _ -> updateChart())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);  // Loop indefinitely
        timeline.play();
    }
    private void updateChart(){
        barChart.getData().clear();
        populateChart();
    }

    private void populateChart() {
        for (var product : Main.dataHandler.getProducts()) {
            if (product.isSelected()) {
                XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
                productSeries.setName(product.getName());
                for (var country : Main.dataHandler.getSalaries()) {
                    if (country.isSelected()) {

                        double price = product.getPrice(country.getName());
                        System.out.println("Product price: " + price);
                        double salary = country.getSalary();
                        System.out.println("Product salary: " + salary);
                        int productsCount = (int) (salary / price);

                        XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(country.getName(), (double) productsCount);
                        productSeries.getData().add(dataPoint);

                        dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
                            if (newNode != null) {
                                Tooltip tooltip = new Tooltip(
                                        "Country: " + country + "\n" +
                                                "Average Salary: $" + salary + "\n" +
                                                "Product Price: $" + price + "\n" +
                                                "Product Purchasable: " + productsCount
                                );
                                Tooltip.install(newNode, tooltip);
                            }
                        });
                    }
                }
                barChart.getData().add(productSeries);
            }

        }

    }
}

