package org.zerofruit.ecommerce.domain.order;

import org.springframework.stereotype.Component;
import org.zerofruit.ecommerce.domain.store.*;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class OrderValidator {
    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    public OrderValidator(
            ProductRepository productRepository,
            StoreRepository storeRepository
    ) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public void validate(Order order) {
        validate(order, getStore(order), getProducts(order));
    }

    void validate(Order order, Store store, Map<Long, Product> products) {
        if (!store.isOpen()) {
            throw new IllegalArgumentException("Store is not opened");
        }

        if (order.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Order item is empty");
        }

        if (!store.isValidOrderAmount(order.calculateTotalPrice())) {
            throw new IllegalStateException("Order price is no larger than minimum order price");
        }

        for (OrderItem item: order.getOrderItems()) {
            validateOrderItem(item, products.get(item.getProductId()));
        }
    }

    private void validateOrderItem(OrderItem item, Product product) {
        if (!product.getName().equals(item.getName())) {
            throw new IllegalArgumentException("Product has changed");
        }

        for (OrderOptionGroup group : item.getGroups()) {
            validateOrderOptionGroup(group, product);
        }
    }

    private void validateOrderOptionGroup(OrderOptionGroup group, Product product) {
        for (OptionGroupSpecification spec : product.getOptionGroupSpecs()) {
            if (spec.isSatisfiedBy(group.convertToOptionGroup())) {
                return;
            }
        }

        throw new IllegalArgumentException("Product has changed");
    }

    private Store getStore(Order order) {
        return storeRepository.findById(order.getStoreId()).orElseThrow(IllegalArgumentException::new);
    }

    private Map<Long, Product> getProducts(Order order) {
        return productRepository
                .findAllById(order.getProductIds())
                .stream()
                .collect(toMap(Product::getId, identity()));
    }
}
