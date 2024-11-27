package org.example.globalgoodsindex.core.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FetchData {

    public boolean scrapeSalaries(String outputFile) {
        System.out.println("Starting scrapeSalaries method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp?displayCurrency=USD&itemId=105";

        try {
            // Connect to the URL and get the document
            Document doc = Jsoup.connect(url).get();
            //System.out.println(doc.body());

            // Select all rows within the table body
            Elements rows = doc.select("table#t2 tbody tr");

            // List to store the scraped data
            List<String[]> salaries = new ArrayList<>();

            for (Element row : rows) {
                //   System.out.println(row.text());
                Elements cells = row.select("td");
                //  System.out.println(cells);

                if (cells.size() >= 3) {
                    String country = cells.get(1).text();
                    String salary = cells.get(2).text();
                    salaries.add(new String[]{country, salary});
                }
            }


            // Write the sorted data alphabetically to the output file
            salaries.sort(Comparator.comparing(s -> s[0]));
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (String[] salary : salaries) {
                    System.out.println(salary[0] + "," + salary[1]);
                    writer.println(salary[0] + "," + salary[1]);
                }
                System.out.println("Salaries saved to " + outputFile);
            }

            System.out.println("Salaries successfully scraped and saved.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void scrapeMarketProducts(String outputFile) {
        System.out.println("Starting scrapeMarketProducts method");

        String url = "https://www.numbeo.com/cost-of-living/prices_by_country.jsp";

        try {
            // Connect to the URL and get the document
            Document doc = Jsoup.connect(url).get();
            //System.out.println(doc.body());

            Element dropdown = doc.selectFirst("select#itemId");
            if (dropdown == null) {
                System.out.println("Dropdown with id 'itemId' not found.");
                return;
            }

            Elements options = dropdown.select("option");


            List<String[]> marketProducts = new ArrayList<>();

            for (Element option : options) {
                String optionText = option.text();
                if (optionText.contains("Markets")) {
                    String productName = optionText.split(":")[1].trim();
                    String productValue = option.attr("value").trim();

                    marketProducts.add(new String[]{productName, productValue});
                }
            }

            // Write the sorted data alphabetically to the output file
            marketProducts.sort(Comparator.comparing(s -> s[0]));
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (String[] product : marketProducts) {
                    System.out.println(product[0] + "," + product[1]);
                    writer.println(product[0] + "," + product[1]);
                }
                System.out.println("Salaries saved to " + outputFile);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
