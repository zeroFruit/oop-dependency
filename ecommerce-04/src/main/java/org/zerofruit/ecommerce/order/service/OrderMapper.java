package org.zerofruit.ecommerce.order.service;

import org.springframework.stereotype.Component;
import org.zerofruit.ecommerce.order.domain.Order;
import org.zerofruit.ecommerce.order.domain.OrderItem;
import org.zerofruit.ecommerce.order.domain.OrderOption;
import org.zerofruit.ecommerce.order.domain.OrderOptionGroup;
import org.zerofruit.ecommerce.store.domain.Product;
import org.zerofruit.ecommerce.store.domain.ProductRepository;
import org.zerofruit.ecommerce.store.domain.Store;
import org.zerofruit.ecommerce.store.domain.StoreRepository;

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
                .storeId(store.getId())
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
                .productId(product.getId())
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
