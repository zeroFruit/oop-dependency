package org.zerofruit.ecommerce.service.order;

import org.springframework.stereotype.Component;
import org.zerofruit.ecommerce.domain.order.Order;
import org.zerofruit.ecommerce.domain.order.OrderItem;
import org.zerofruit.ecommerce.domain.order.OrderOption;
import org.zerofruit.ecommerce.domain.order.OrderOptionGroup;
import org.zerofruit.ecommerce.domain.store.Product;
import org.zerofruit.ecommerce.domain.store.ProductRepository;
import org.zerofruit.ecommerce.domain.store.Store;
import org.zerofruit.ecommerce.domain.store.StoreRepository;

import java.time.LocalDateTime;

import static java.util.stream.Collectors.toList;

@Component
public class OrderMapper {
    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    public OrderMapper(ProductRepository productRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public Order mapFrom(Cart cart) {
        Store store = storeRepository.findById(cart.getStoreId())
                .orElseThrow(IllegalArgumentException::new);

        return Order
                .builder()
                .userId(cart.getUserId())
                .store(store)
                .orderedTime(LocalDateTime.now())
                .orderItems(
                        cart.getCartItems()
                                .stream()
                                .map(this::toOrderItem)
                                .collect(toList())
                )
                .build();
    }

    private OrderItem toOrderItem(Cart.CartItem cartItem) {
        Product product = productRepository.findById(cartItem.getProductId())
                .orElseThrow(IllegalArgumentException::new);

        return OrderItem
                .builder()
                .product(product)
                .name(cartItem.getName())
                .count(cartItem.getCount())
                .groups(
                        cartItem.getGroups()
                                .stream()
                                .map(this::toOrderItemOptionGroup)
                                .collect(toList())
                )
                .build();
    }

    private OrderOptionGroup toOrderItemOptionGroup(Cart.CartOptionGroup cartOptionGroup) {
        return OrderOptionGroup
                .builder()
                .name(cartOptionGroup.getName())
                .orderOptions(
                        cartOptionGroup.getOptions()
                                .stream()
                                .map(this::toOrderItemOption)
                                .collect(toList())
                )
                .build();
    }

    private OrderOption toOrderItemOption(Cart.CartOption cartOption) {
        return OrderOption
                .builder()
                .name(cartOption.getName())
                .price(cartOption.getPrice())
                .build();
    }
}
