package org.example.globalgoodsindex.core.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.globalgoodsindex.core.services.CSVReader;

import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class DataHandler {

    private final ObservableList<Salaries> salaries;
    private final ObservableList<Product> products;


    public DataHandler() {
        this.salaries = FXCollections.observableArrayList(CSVReader.readCSVToList("/data/salaries/salaries.txt"));
        this.products = FXCollections.observableArrayList();
        populateProducts();

    }

    private void populateProducts() {
        try {

            // Access the resource directory as a stream
            URL resource = getClass().getResource("/data/products/");
            if (resource == null) {
                throw new IllegalStateException("Resource '/data/products/' not found.");
            }

            // Handle resources inside the JAR
            URI uri = resource.toURI();
            if ("jar".equals(uri.getScheme())) {
                try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                     Stream<Path> paths = Files.walk(fileSystem.getPath("/data/products/"))) {
                    paths.filter(Files::isRegularFile)
                            .forEach(path -> this.products.add(new Product(path.getFileName().toString())));
                }
            } else {

                // Handle resources on the filesystem (e.g., during development)
                try (Stream<Path> paths = Files.walk(Paths.get(uri))) {
                    paths.filter(Files::isRegularFile)
                            .forEach(path -> this.products.add(new Product(path.getFileName().toString())));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load products from '/data/products/'", e);
        }
    }

    public List<Salaries> getSalaries() {
        return salaries;
    }

    public List<Product> getProducts() {
        return products;
    }

}
