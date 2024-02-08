package refactoring.repository;

import refactoring.domain.Product;

public interface ProductCatalog {
    Product getById(Integer id);
}
