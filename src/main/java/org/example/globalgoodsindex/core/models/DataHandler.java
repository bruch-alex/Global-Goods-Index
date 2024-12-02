package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class DataHandler {

    private final ObservableList<Salary> salaries;
    private final ObservableList<Product> products;


    public DataHandler() {
        this.salaries = FXCollections.observableArrayList(CSVReader.readCSVToList("/data/salaries/salaries.txt"));
        this.products = FXCollections.observableArrayList();
        populateProducts();
    }

    /**
     * needs refactoring
     */
    private void populateProducts() {
        URI uri;
        URL url = getClass().getResource("/data/products/apples.txt");
        if (url == null) {
            System.out.println("url is null");
            return;
        }
        try {
            uri = url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path myPath;
        FileSystem fs;
        if (uri.getScheme().equals("jar")) {
            System.out.println("Scheme is JAR");
            try {
                fs = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fs.getPath("/data/products");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else myPath = Paths.get(uri).getParent();
        Stream<Path> walk;
        try {
            walk = Files.walk(myPath, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        walk.filter(Files::isRegularFile)
                .forEach(s -> this.products.add(new Product(s.getFileName().toString())));
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }

}
