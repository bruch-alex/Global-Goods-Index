package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;

public class BarChartController {

    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        bindStrings();
        setupProductListeners();
        setupSalariesListeners();
    }

    @FXML
    private void bindStrings(){
        xAxis.labelProperty().bind(I18N.createStringBinding("country"));
        yAxis.labelProperty().bind(I18N.createStringBinding("amount"));
    }

    @FXML
    private void setupProductListeners() {
        for (Product product : Main.dataHandler.getProducts()) {
            product.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    addProductToChart(product);
                } else {
                    removeProductFromChart(product);
                }
            });
        }
    }

    private void setupSalariesListeners() {
        for (Salaries salary : Main.dataHandler.getSalaries()) {
            salary.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    addSalariesToChart(salary);
                } else {
                    removeSalariesFromChart(salary);
                }
            });
        }
    }

    private void addProductToChart(Product product) {
        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.setName(product.getName());

        for (Salaries salaries : Main.dataHandler.getSalaries()) {
            if (salaries.isSelected()) {
                addDataPoint(productSeries, product, salaries);
            }
        }

        barChart.getData().add(productSeries);
    }

    private void removeProductFromChart(Product product) {
        barChart.getData().removeIf(series -> series.getName().equals(product.getName()));
    }

    private void addSalariesToChart(Salaries salaries) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {

            Product matchedProduct = findProductByName(series.getName());

            if (matchedProduct != null) {
                addDataPoint(series, matchedProduct, salaries);
            }
        }
    }

    private void removeSalariesFromChart(Salaries salaries) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            series.getData().removeIf(data -> data.getXValue().equals(salaries.getName()));
        }
    }

    private void addDataPoint(XYChart.Series<String, Number> series, Product product, Salaries salary) {
        double price = product.getPrice(salary.getName());
        double salaryValue = salary.getSalary();
        int productsCount = (int) (salaryValue / price);

        XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(salary.getName(), (double) productsCount);
        series.getData().add(dataPoint);

        dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
            if (newNode != null) {
                Tooltip tooltip = createTooltip(salary.getName(), salaryValue, price, productsCount);
                tooltip.setShowDelay(Duration.millis(0));
                Tooltip.install(newNode, tooltip);
            }
        });
    }

    private Product findProductByName(String name) {
        for (Product product : Main.dataHandler.getProducts()) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }

    private Tooltip createTooltip(String countryName, double salary, double price, int productsCount) {
        return new Tooltip(
                "Country: " + countryName + "\n" +
                        "Average Salary: $" + salary + "\n" +
                        "Product Price: $" + price + "\n" +
                        "Product Purchasable: " + productsCount
        );
    }
}

