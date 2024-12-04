package org.example.globalgoodsindex.services;

import org.example.globalgoodsindex.models.Goods;
import org.example.globalgoodsindex.models.Salary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FetchData {

    public static List<Salary> scrapeSalaries() {

        String url = AppConfig.get("numeo.url.average-salary");
        List<Salary> salariesData = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table#t2 tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.size() >= 3) {
                    String country = cells.get(1).text().replace(" ", "_");
                    String salaryText = cells.get(2).text();

                    try {
                        double salary = Double.parseDouble(salaryText.replace(",", "").trim());
                        salariesData.add(new Salary(country, salary));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid salary format for country: " + country);
                    }
                }
            }

            // Sort alphabetically by country name
            salariesData.sort(Comparator.comparing(Salary::getName));

        } catch (IOException e) {
            System.err.println("Can't connect to Numbeo");
            return null;
        }

        return salariesData;
    }

    public static List<List<String>> scrapeProducts() {

        String baseUrl = AppConfig.get("numeo.url.products-data");
        List<List<String>> productsData = new ArrayList<>();
        StringBuilder url = new StringBuilder(baseUrl);

        for (var v : Goods.values()) {
            url.append("&itemId=").append(v.getItemId());
        }
        try {

            Document doc = Jsoup.connect(url.toString()).get();

            // Extract headers
            Elements headers = doc.select("thead tr th");
            List<String> columnHeaders = new ArrayList<>();
            for (int i = 2; i < headers.size(); i++) { // Skip the first column (Rank)
                String headerText = headers.get(i).text().trim().toLowerCase();
                headerText = headerText.replaceAll("[().,-]", "").replaceAll("[ ]", "_");
                columnHeaders.add(headerText);
            }

            // Extract product data rows
            Elements rows = doc.select("tbody tr");
            List<List<String>> data = new ArrayList<>();

            for (Element row : rows) {
                Elements cells = row.select("td");
                if (cells.size() < columnHeaders.size()) continue; // skip incomplete rows

                List<String> rowData = new ArrayList<>();

                String country = cells.get(1).text().trim().replace(" ", "_");
                rowData.add(country);

                for (int i = 2; i < cells.size(); i++) {
                    String price = cells.get(i).text().trim();
                    rowData.add(price.isEmpty() ? "-" : price);
                }
                data.add(rowData);
            }

            // sort alphabetically by country name
            data.sort((row1, row2) -> row1.get(0).compareToIgnoreCase(row2.get(0)));

            productsData.add(columnHeaders);
            productsData.addAll(data);


        } catch (IOException e) {
            System.err.println("Can't connect to Numbeo");
            return null;
        }

        return productsData;

    }
}
