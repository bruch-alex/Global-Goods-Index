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
        System.out.println("Starting scrapeSalaries method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=105";
        List<Salaries> salaries = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();
            Elements rows = doc.select("table#t2 tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                if (cells.size() >= 3) {
                    String country = cells.get(1).text();
                    String standardizedCountry = country.replace(" ", "_");
                    String salaryText = cells.get(2).text();

                    try {
                        double salary = Double.parseDouble(salaryText.replace(",", "").trim());
                        salaries.add(new Salaries(standardizedCountry, salary));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid salary format for country: " + country);
                    }
                }
            }

            // Sort alphabetically by country name
            salaries.sort(Comparator.comparing(Salaries::getName));
            System.out.println("Salaries successfully scraped.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return salaries;
    }

    public List<List<String>> scrapeProducts() {
        System.out.println("Starting scrapeProducts method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=110&itemId=118&itemId=121&itemId=14&itemId=19&itemId=17&itemId=15&itemId=11&itemId=16&itemId=113&itemId=9&itemId=12&itemId=8&itemId=119&itemId=111&itemId=112&itemId=115&itemId=116&itemId=13";

        try {
            Document doc = Jsoup.connect(url).get();

            // Extract headers
            List<String> columnHeaders = extractHeaders(doc);

            // Extract product data rows
            List<List<String>> data = extractProductData(doc, columnHeaders);

            // Combine headers and data
            List<List<String>> combinedData = new ArrayList<>();
            combinedData.add(columnHeaders);
            combinedData.addAll(data);

            System.out.println("Products successfully scraped.");
            System.out.println("scraped-data: " + combinedData);
            return combinedData;
        } catch (Exception e) {
            System.err.println("Error scraping products: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list on failure
        }
    }

    private List<String> extractHeaders(Document doc) {
        Elements headers = doc.select("thead tr th");
        List<String> columnHeaders = new ArrayList<>();
        for (int i = 1; i < headers.size(); i++) { // Skip the first column (Rank)
            String headerText = headers.get(i).text().trim();
            columnHeaders.add("\"" + headerText.replaceAll("\\s+", " ") + "\"");
        }
        return columnHeaders;
    }

    private List<List<String>> extractProductData(Document doc, List<String> columnHeaders) {
        Elements rows = doc.select("tbody tr");
        List<List<String>> data = new ArrayList<>();

        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() < columnHeaders.size()) continue; // Skip incomplete rows

            List<String> rowData = new ArrayList<>();

            // Extract and standardize country name
            String country = cells.get(1).text().trim();
            String standardizedCountry = country.replace(" ", "_");
            rowData.add(standardizedCountry);

            for (int i = 2; i < cells.size(); i++) {
                String price = cells.get(i).text().trim();
                rowData.add(price.isEmpty() ? "-" : price);
            }
            data.add(rowData);
        }
        data.sort((row1, row2) -> row1.get(0).compareToIgnoreCase(row2.get(0)));

        return data;
    }
}


//    public void scrapeProducts(String outputFile) {
//
//        System.out.println("Starting scrapeProducts method");
//
//        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=110&itemId=118&itemId=121&itemId=14&itemId=19&itemId=17&itemId=15&itemId=11&itemId=16&itemId=113&itemId=9&itemId=12&itemId=8&itemId=119&itemId=111&itemId=112&itemId=115&itemId=116&itemId=13";
//
//        try {
//            Document doc = Jsoup.connect(url).get();
//
//            Elements headers = doc.select("thead tr th");
//            List<String> columnHeaders = new ArrayList<>();
//
//
//            // Skip the first column (Rank) and extract others
//            for (int i = 1; i < headers.size(); i++) {
//                String headerText = headers.get(i).text().replaceAll("\\s+", " ").trim();
//                String quotedHeaderText = "\"" + headerText + "\"";
//                columnHeaders.add(quotedHeaderText);
//                //System.out.println("headers" + quotedHeaderText);
//            }
//
//            // Extract the table body rows (country and prices)
//            Elements rows = doc.select("tbody tr");
//
//            List<List<String>> data = new ArrayList<>();
//
//            for (int i = 1; i < rows.size(); i++) {
//                Element row = rows.get(i);
//                Elements cells = row.select("td");
//
//                // Skip empty rows
//                if (cells.size() < columnHeaders.size()) continue;
//
//                List<String> rowData = new ArrayList<>();
//                String country = cells.get(1).text().trim();
//                rowData.add(country);
//
//                // Extract prices for each product
//                for (int j = 2; j < cells.size(); j++) {
//                    String price = cells.get(j).text().trim();
//                    rowData.add(price.isEmpty() ? "-" : price); // Use "-" if no data
//
//                }
//
//                data.add(rowData);
//            }
//
//
//            //sort
//            data.sort((row1, row2) -> row1.get(0).compareToIgnoreCase(row2.get(0)));
//
/// /            for (List<String> row : data) {
/// /                System.out.println("check received data: " + row);
/// /            }
//
//            // Write to CSV
//            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
//                // Write header
//                writer.println(String.join(",", columnHeaders));
//
//                // Write data rows
//                for (List<String> row : data) {
//                    writer.println(String.join(",", row));
//                }
//
//                System.out.println("Data saved to " + outputFile);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


