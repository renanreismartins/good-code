package refactoring.controller;

import refactoring.domain.Category;
import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.domain.Product;
import refactoring.doubles.InMemoryProductCatalog;
import refactoring.doubles.TestOrderRepository;
import refactoring.repository.ProductCatalog;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderCreationUseCaseTest {
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private Category food = new Category() {{
        setName("food");
        setTaxPercentage(10D);
    }};;
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            Arrays.<Product>asList(
                    new Product() {{
                        setId(1);
                        setName("salad");
                        setPrice(3.56D);
                        setCategory(food);
                    }},
                    new Product() {{
                        setId(2);
                        setName("tomato");
                        setPrice(4.65D);
                        setCategory(food);
                    }}
            )
    );
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {
        SellItemRequest saladRequest = new SellItemRequest();
        saladRequest.setId(1);
        saladRequest.setQuantity(2);

        SellItemRequest tomatoRequest = new SellItemRequest();
        tomatoRequest.setId(2);
        tomatoRequest.setQuantity(3);

        final SellItemsRequest request = new SellItemsRequest();
        request.setRequests(new ArrayList<>());
        request.getRequests().add(saladRequest);
        request.getRequests().add(tomatoRequest);

        useCase.run(request);

        final Order insertedOrder = orderRepository.getSavedOrder();
        assertThat(insertedOrder.getStatus()).isEqualTo(OrderStatus.CREATED);
        assertThat(insertedOrder.getCurrency()).isEqualTo("EUR");
        assertThat(insertedOrder.getItems()).hasSize(2);
        assertThat(insertedOrder.getItems().get(0).getProduct().getName()).isEqualTo("salad");
        assertThat(insertedOrder.getItems().get(0).getQuantity()).isEqualTo(2);
        assertThat(insertedOrder.getItems().get(1).getProduct().getName()).isEqualTo("tomato");
        assertThat(insertedOrder.getItems().get(1).getQuantity()).isEqualTo(3);
    }

    @Test
    public void unknownProduct() throws Exception {
        SellItemsRequest request = new SellItemsRequest();
        request.setRequests(new ArrayList<>());
        SellItemRequest unknownProductRequest = new SellItemRequest();
        unknownProductRequest.setId(-1);
        request.getRequests().add(unknownProductRequest);

        assertThatThrownBy(() -> useCase.run(request)).isExactlyInstanceOf(UnknownProductException.class);
    }
}
