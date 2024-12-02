package org.example.globalgoodsindex.core.models;

import org.example.globalgoodsindex.core.services.CSVReader;
import org.example.globalgoodsindex.core.services.L10N;

import java.util.List;

public class Product extends Entry {

    private final List<Salaries> prices;

    public Product(String name, List<Salaries> prices) {
        super(name);
        this.prices = prices;
    }

    public List<Salaries> getPrices() {
        return prices;
    }


    public double getPrice(String countryName) {
        return prices.stream()
                .filter(s -> s.getName().equals(countryName))
                .findFirst()
                .map(Salaries::getSalary)
                .orElse(-1.0); // Return -1.0 if country not found
    }

    @Override
    public String toString() {
        return "Product{name='" + getLocalizedName() + "', prices=" + prices + '}';
    }

}
