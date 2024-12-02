package org.example.globalgoodsindex.controllers;

import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Product;
import org.example.globalgoodsindex.core.models.Salaries;
import org.example.globalgoodsindex.core.services.L10N;
import org.example.globalgoodsindex.core.utils.ToastUtil;

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
    private void bindStrings() {
        xAxis.labelProperty().bind(L10N.createStringBinding("country"));
        yAxis.labelProperty().bind(L10N.createStringBinding("amount"));
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

        StringBinding shortNameBinding = L10N.getShortNameBinding(product);
        productSeries.nameProperty().bind(shortNameBinding);

        boolean hasValidData = false;

        for (Salaries salaries : Main.dataHandler.getSalaries()) {
            if (salaries.isSelected()) {
                if (product.getPrice(salaries.getName()) > 0) { // Check if valid price exists
                    addDataPoint(productSeries, product, salaries);
                    hasValidData = true;
                } else {
                    triggerToast("No price data available for:\n" +
                            "Product = " + shortNameBinding.get() + "\n" +
                            "Country = " + salaries.getName());


                    // TODO:  Uncheck the product in the UI
                    product.setSelected(false);

                    System.out.println("Unchecking product: " + product.getName());
                    return;
                }
            }
        }

        if (hasValidData) {
            barChart.getData().add(productSeries);
        } else {
            System.out.println("No valid data available for product: " + product.getName());
        }
    }


    private void removeProductFromChart(Product product) {
        String shortName = L10N.getShortNameBinding(product).get();
        barChart.getData().removeIf(series -> series.getName().equals(shortName));
    }

    private void addSalariesToChart(Salaries salaries) {

        for (XYChart.Series<String, Number> series : barChart.getData()) {
            Product matchedProduct = findProductByShortName(series.getName());

            if (matchedProduct != null) {
                addDataPoint(series, matchedProduct, salaries);
            }
        }
    }

    private void removeSalariesFromChart(Salaries salaries) {
        for (XYChart.Series<String, Number> series : barChart.getData()) {
            series.getData().removeIf(data -> data.getXValue().equals(salaries.getLocalizedName()));
        }
    }

    private void addDataPoint(XYChart.Series<String, Number> series, Product product, Salaries salary) {
        double price = product.getPrice(salary.getName());
        double salaryValue = salary.getSalary();
        int productsCount = (int) (salaryValue / price);


        XYChart.Data<String, Number> dataPoint = new XYChart.Data<>();

        //dataPoint.XValueProperty().bind(L10N.createStringBinding(salary.getName()));
        dataPoint.XValueProperty().bind(salary.nameProperty());
        dataPoint.setYValue((double) productsCount);

        series.getData().add(dataPoint);

        dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
            if (newNode != null) {
                Tooltip tooltip = createTooltip(salary.getName(), salaryValue, price, productsCount);
                tooltip.setShowDelay(Duration.millis(0));
                Tooltip.install(newNode, tooltip);
            }
        });
    }

    private Product findProductByShortName(String shortName) {
        for (Product product : Main.dataHandler.getProducts()) {
            String shortNameKey = product.getName() + ".short";
            if (L10N.get(shortNameKey).equals(shortName)) {
                return product;
            }
        }
        return null;
    }

    //TODO: implement localization
    private Tooltip createTooltip(String countryName, double salary, double price, int productsCount) {
        return new Tooltip(
                "Country: " + countryName + "\n" +
                        "Average Salary: $" + salary + "\n" +
                        "Product Price: $" + price + "\n" +
                        "Product Purchasable: " + productsCount
        );
    }

    private void triggerToast(String message) {
        ToastUtil.showToast(Main.primaryStage, message, 8000);
    }
}

