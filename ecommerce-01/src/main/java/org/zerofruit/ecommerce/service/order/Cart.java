package org.zerofruit.ecommerce.service.order;

import lombok.*;
import org.zerofruit.ecommerce.domain.generic.money.Money;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class Cart {
    private Long storeId;
    private Long userId;
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(Long storeId, Long userId, CartItem... cartItems) {
        this.storeId = storeId;
        this.userId = userId;
        this.cartItems = Arrays.asList(cartItems);
    }

    @Data
    @NoArgsConstructor
    public static class CartItem {
        private Long productId;
        private String name;
        private int count;
        private List<CartOptionGroup> groups = new ArrayList<>();

        public CartItem(Long productId, String name, int count, CartOptionGroup... groups) {
            this.productId = productId;
            this.name = name;
            this.count = count;
            this.groups = Arrays.asList(groups);
        }
    }

    @Data
    @NoArgsConstructor
    public static class CartOptionGroup {
        private String name;
        private List<CartOption> options = new ArrayList<>();

        public CartOptionGroup(String name, CartOption ... options) {
            this.name = name;
            this.options = Arrays.asList(options);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartOption {
        private String name;
        private Money price;
    }
}
