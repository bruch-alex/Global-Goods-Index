package org.example.globalgoodsindex.controllers;


import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tooltip;
import org.example.globalgoodsindex.Main;
import org.example.globalgoodsindex.core.models.Product;


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
        populateChart();
    }

    private void populateChart() {
        XYChart.Series<String, Number> productSeries = new XYChart.Series<>();
        productSeries.setName("Product name");

        for (var country : Main.dataHandler.getSalaries()) {
            if(country.isSelected()){

                for(var product : Main.dataHandler.getProducts()){
                    if(product.isSelected()){

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
            }

//            double price = productDetails.get(country);
//            double salary = avgSalaries.get(country);
//
//            int burgersCount = (int) (salary / price);

//            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(country, (double) burgersCount);

//            productSeries.getData().add(dataPoint);

            // addListener event it's triggered whenever the nodeProperty() value changes
//            dataPoint.nodeProperty().addListener((observable, oldNode, newNode) -> {
//                if (newNode != null) {
//                    Tooltip tooltip = new Tooltip(
//                            "Country: " + country + "\n" +
//                                    "Average Salary: $" + salary + "\n" +
//                                    "Product Price: $" + price + "\n" +
//                                    "Product Purchasable: " + burgersCount
//                    );
//                    Tooltip.install(newNode, tooltip);
//                }
//            });
        }
        barChart.getData().add(productSeries);
    }

}

