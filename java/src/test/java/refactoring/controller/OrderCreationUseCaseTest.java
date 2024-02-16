package refactoring.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import refactoring.domain.Category;
import refactoring.domain.Item;
import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.domain.Product;
import refactoring.doubles.InMemoryProductCatalog;
import refactoring.doubles.TestOrderRepository;
import refactoring.repository.ProductCatalog;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderCreationUseCaseTest {
    private TestOrderRepository orderRepository;
	private OrderCreationUseCase useCase;

    @BeforeEach
    public void setup() {
        Category food = new Category();
        food.setName("food");
        food.setTaxPercentage(10D);

        Product salad = new Product();
        salad.setId(1);
        salad.setName("salad");
        salad.setPrice(3.56D);
        salad.setCategory(food);

        Product tomato = new Product();
        tomato.setId(2);
        tomato.setName("tomato");
        tomato.setPrice(4.65D);
        tomato.setCategory(food);

	    ProductCatalog productCatalog = new InMemoryProductCatalog(List.of(salad, tomato));
        orderRepository = new TestOrderRepository();
        useCase = new OrderCreationUseCase(orderRepository, productCatalog);
    }

    @Test
    public void sellMultipleItems() {
        Item saladRequest = new Item();
        saladRequest.setProductId(1);
        saladRequest.setQuantity(2);

        Item tomatoRequest = new Item();
        tomatoRequest.setProductId(2);
        tomatoRequest.setQuantity(3);

        List<Item> requests = new ArrayList<>();
        requests.add(saladRequest);
        requests.add(tomatoRequest);

        useCase.run(requests);

        final Order insertedOrder = orderRepository.getSavedOrder();
        assertThat(insertedOrder.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(insertedOrder.getCurrency()).isEqualTo("EUR");
        assertThat(insertedOrder.getItems()).hasSize(2);
        assertThat(insertedOrder.getItems().get(0).getProductId()).isEqualTo(1);
        assertThat(insertedOrder.getItems().get(0).getQuantity()).isEqualTo(2);
        assertThat(insertedOrder.getItems().get(1).getProductId()).isEqualTo(2);
        assertThat(insertedOrder.getItems().get(1).getQuantity()).isEqualTo(3);
    }

    @Test
    public void unknownProduct() {
        List<Item> requests = new ArrayList<>();
        Item unknownProductRequest = new Item();
        unknownProductRequest.setProductId(-1);
        requests.add(unknownProductRequest);

        assertThatThrownBy(() -> useCase.run(requests)).isExactlyInstanceOf(UnknownProductException.class);
    }
}
