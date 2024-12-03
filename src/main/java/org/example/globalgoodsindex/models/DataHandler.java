package org.example.globalgoodsindex.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.services.FetchData;

import java.util.ArrayList;
import java.util.List;


public class DataHandler {

    // Load data
    private final String salariesResourcePath = "/data/salaries/salaries.csv";
    private final String productsResourcePath = "/data/products/products.csv";

    private final ObservableList<Salary> salaries;
    private final ObservableList<Product> products = FXCollections.observableArrayList();


    public DataHandler() {
        this.salaries = FXCollections.observableArrayList(FetchData.scrapeSalaries());
        parseProducts(FetchData.scrapeProducts());
    }

    public void parseProducts(List<List<String>> products) {
        products.forEach(System.out::println);
        for (int i = 0; i < products.getFirst().size(); i++) {
            Product p = new Product(products.getFirst().get(i));

            List<Salary> tempPrices = new ArrayList<>();

            for (int j = 1; j < products.size(); j++) {
                String countryName = products.get(j).getFirst();
                double price = products.get(j).get(i+1).equals("-") ? -1 : Double.parseDouble(products.get(j).get(i+1));
                tempPrices.add(new Salary(countryName, price));
            }
            p.addPrices(tempPrices);
            this.products.add(p);
        }
    }

    /**
     * needs refactoring
     */
//    private void populateProducts() {
//        URI uri;
//        URL url = getClass().getResource("/data/products/apples.txt");
//        if (url == null) {
//            System.out.println("url is null");
//            return;
//        }
//        try {
//            uri = url.toURI();
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        Path myPath;
//        FileSystem fs;
//        if (uri.getScheme().equals("jar")) {
//            System.out.println("Scheme is JAR");
//            try {
//                fs = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
//                myPath = fs.getPath("/data/products");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } else myPath = Paths.get(uri).getParent();
//        Stream<Path> walk;
//        try {
//            walk = Files.walk(myPath, 1);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        walk.filter(Files::isRegularFile)
//                .forEach(s -> this.products.add(new Product(s.getFileName().toString())));
//    }
    public List<Salary> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }

}
