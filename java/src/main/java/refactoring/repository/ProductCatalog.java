package refactoring.repository;

import refactoring.domain.Product;

public interface ProductCatalog {
    Product getByName(String name);
}
