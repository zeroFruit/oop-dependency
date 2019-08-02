package org.zerofruit.ecommerce.service.store;

import lombok.Data;
import org.zerofruit.ecommerce.domain.generic.money.Money;
import org.zerofruit.ecommerce.domain.store.Product;
import org.zerofruit.ecommerce.domain.store.Store;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductBoard {
    private Long storeId;
    private String storeName;
    private boolean open;
    private Money minOrderAmount;
    private List<ProductItem> productItems;

    public ProductBoard(Store store) {
        this.storeId = store.getId();
        this.storeName = store.getName();
        this.open = store.isOpen();
        this.minOrderAmount = store.getMinOrderAmount();
    }

    private List<ProductItem> toProductItems(List<Product> products) {
        return products
                .stream()
                .map(ProductItem::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class ProductItem {
        private Long productId;
        private String productName;
        private Money productBasePrice;
        private String productDescription;

        public ProductItem(Product product) {
            this.productId = product.getId();
            this.productName = product.getName();
            this.productBasePrice = product.getBasePrice();
            this.productDescription = product.getDescription();
        }
    }
}
