package br.com.devjmcn.projetoguarani.model.models;

import java.util.List;

public class Product {
    private String cod;
    private String desc;
    private Double stock;
    private List<String> prices;

    public Product(String cod, String desc, Double stock, List<String> prices) {
        this.cod = cod;
        this.desc = desc;
        this.stock = stock;
        this.prices = prices;
    }

    public String getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }

    public Double getStock() {
        return stock;
    }

    public List<String> getPrices() {
        return prices;
    }

    public Product copy(Product product, List<String> listPrice) {
        return new Product(
                product.cod,
                product.desc,
                product.stock,
                listPrice
        );
    }
}
