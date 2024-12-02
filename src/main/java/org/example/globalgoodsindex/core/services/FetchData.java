package org.example.globalgoodsindex.core.services;

import org.example.globalgoodsindex.core.models.Salaries;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FetchData {

    public List<Salaries> scrapeSalaries() {
        // System.out.println("Starting scrapeSalaries method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=105";
        List<Salaries> salariesData = new ArrayList<>();

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
                        salariesData.add(new Salaries(country, salary));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid salary format for country: " + country);
                    }
                }
            }

            // Sort alphabetically by country name
            salariesData.sort(Comparator.comparing(Salaries::getName));

            //  System.out.println("Salaries successfully scraped.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return salariesData;
    }

    public List<List<String>> scrapeProducts() {
        //System.out.println("Starting scrapeProducts method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=110&itemId=118&itemId=121&itemId=14&itemId=19&itemId=17&itemId=15&itemId=11&itemId=16&itemId=113&itemId=9&itemId=12&itemId=8&itemId=119&itemId=111&itemId=112&itemId=115&itemId=116&itemId=13";
        List<List<String>> productsData = new ArrayList<>();

        try {

            Document doc = Jsoup.connect(url).get();

            // Extract headers
            Elements headers = doc.select("thead tr th");
            List<String> columnHeaders = new ArrayList<>();
            for (int i = 1; i < headers.size(); i++) { // Skip the first column (Rank)
                String headerText = headers.get(i).text().trim();
                columnHeaders.add("\"" + headerText.replaceAll("\\s+", " ") + "\"");
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


            //   System.out.println("Products successfully scraped.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productsData;

    }
}