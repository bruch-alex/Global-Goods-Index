package org.example.globalgoodsindex.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.example.globalgoodsindex.App;
import org.example.globalgoodsindex.models.Product;
import org.example.globalgoodsindex.models.Salary;
import org.example.globalgoodsindex.services.L10N;

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
        setupLanguageChangeListener();
    }

    @FXML
    private void bindStrings() {
        xAxis.labelProperty().bind(L10N.createStringBinding("country"));
        yAxis.labelProperty().bind(L10N.createStringBinding("amount"));
    }


    private void setupProductListeners() {
        for (Product product : App.dataHandler.getProducts()) {
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
        for (Salary salary : App.dataHandler.getSalaries()) {
            salary.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    addSalariesToChart(salary);
                } else {
                    removeSalariesFromChart(salary);
                }
            });
        }
    }

    /**
     * runs refreshUI function after each language change.
     */
    @FXML
    private void setupLanguageChangeListener() {
        yAxis.labelProperty().addListener((_ -> refreshUI()));
    }

    /**
     * <p>Although every nameProperty is bound with corresponding StringProperty of Product or Salary visual bug is appearing
     * so our team decided to refresh entire UI for now. Maybe can fix later.
     *
     * <p>If u want to check the bug: In initialize() method comment out call to the method setupLanguageChangeListener() - line 30
     */
    @FXML
    private void refreshUI() {
        barChart.getData().clear();

        App.dataHandler.getSalaries().stream()
                .filter(Salary::isSelected)
                .forEach(this::addSalariesToChart);

        App.dataHandler.getProducts().stream()
                .filter(Product::isSelected)
                .forEach(this::addProductToChart);
    }

    private void addProductToChart(Product product) {
        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.nameProperty().bind(product.nameProperty());

        for (Salary salary : App.dataHandler.getSalaries()) {
            if (salary.isSelected()) {
                addDataPoint(productSeries, product, salary);
            }
        }

        barChart.getData().add(productSeries);
    }

    private void removeProductFromChart(Product product) {
        barChart.getData().removeIf(series -> series.nameProperty().getValue().equalsIgnoreCase(product.nameProperty().getValue()));
    }

    private void addSalariesToChart(Salary salary) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {

            Product matchedProduct = findProductByName(series.nameProperty().getValue());

            if (matchedProduct != null) {
                addDataPoint(series, matchedProduct, salary);
                barChart.requestLayout();
            }
        }
    }

    private void removeSalariesFromChart(Salary salary) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            series.getData().removeIf(data -> data.XValueProperty().getValue().equalsIgnoreCase(salary.getName()));
        }
    }

    private void addDataPoint(XYChart.Series<String, Number> series, Product product, Salary salary) {
        double price = product.getPrice(salary.getName());
        double salaryValue = salary.getSalary();
        int productsCount = (int) (salaryValue / price);

        XYChart.Data<String, Number> dataPoint = new XYChart.Data<>();

        dataPoint.XValueProperty().bind(salary.nameProperty());
        dataPoint.setYValue((double) productsCount);
        series.getData().add(dataPoint);

        dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
            if (newNode != null) {
                Tooltip tooltip = createTooltip(salary.getName(), product.getName(), salaryValue, price, productsCount);
                tooltip.setShowDelay(Duration.millis(0));
                Tooltip.install(newNode, tooltip);
            }
        });
    }

    private Product findProductByName(String name) {
        for (Product product : App.dataHandler.getProducts()) {
            if (product.nameProperty().getValue().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    private Tooltip createTooltip(String countryName, String productName, double salary, double price, int productsCount) {
        return new Tooltip(
                countryName + ":\n\t" +
                        L10N.get("average_salary") + ": $" + salary + "\n" +
                        productName + ":\n\t" +
                        L10N.get("product_price") + ": $" + price + "\n\t" +
                        L10N.get("product_purchasable") + ": " + productsCount
        );
    }
}

