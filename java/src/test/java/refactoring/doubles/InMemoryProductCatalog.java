package refactoring.doubles;

import refactoring.domain.Product;
import refactoring.repository.ProductCatalog;

import java.util.List;

public class InMemoryProductCatalog implements ProductCatalog {
    private final List<Product> products;

    public InMemoryProductCatalog(List<Product> products) {
        this.products = products;
    }

    public Product getById(Integer id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }
}
