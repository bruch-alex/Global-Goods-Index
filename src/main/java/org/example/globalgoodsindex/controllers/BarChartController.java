package org.example.globalgoodsindex.controllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salarie;


public class BarChartController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        populateChart();

        // ListChangeListener => Called after a change has been made to an ObservableList.
        Main.dataHandler.getSelectedSalaries().addListener((ListChangeListener<Salarie>) change -> updateChart());
        Main.dataHandler.getSelectedProducts().addListener((ListChangeListener<Product>) change -> updateChart());
    }

    private void updateChart() {
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
                                        "Country: " + country.getName() + "\n" +
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

