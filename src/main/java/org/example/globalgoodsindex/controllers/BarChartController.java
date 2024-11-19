package org.example.globalgoodsindex.controllers;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tooltip;


import java.util.HashMap;

public class BarChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    public void initialize() {
        // dummy productDetails for visualization
        HashMap<String, Double> productDetails = new HashMap<>();
        productDetails.put("Switzerland", 6.94);
        productDetails.put("US", 5.81);
        productDetails.put("UK", 5.08);
        productDetails.put("Russia", 1.86);
        productDetails.put("India", 2.36);

        HashMap<String, Double> avgSalaries = new HashMap<>();
        avgSalaries.put("Switzerland", 6500.0);
        avgSalaries.put("US", 5000.0);
        avgSalaries.put("UK", 3500.0);
        avgSalaries.put("Russia", 1200.0);
        avgSalaries.put("India", 600.0);

        populateChart(productDetails, avgSalaries);
    }

    private void populateChart(HashMap<String, Double> productDetails, HashMap<String, Double> avgSalaries) {
        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.setName("Product name");

        for (String country : productDetails.keySet()) {
            double price = productDetails.get(country);
            double salary = avgSalaries.get(country);
            int burgersCount = (int) (salary / price);

            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(country, (double) burgersCount);

            productSeries.getData().add(dataPoint);

            // addListener event it's triggered whenever the nodeProperty() value changes
            dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
                if (newNode != null) {
                    Tooltip tooltip = new Tooltip(
                            "Country: " + country + "\n" +
                                    "Average Salary: $" + salary + "\n" +
                                    "Product Price: $" + price + "\n" +
                                    "Product Purchasable: " + burgersCount
                    );
                    Tooltip.install(newNode, tooltip);
                }
            });
        }

        barChart.getData().add(productSeries);
    }

}

