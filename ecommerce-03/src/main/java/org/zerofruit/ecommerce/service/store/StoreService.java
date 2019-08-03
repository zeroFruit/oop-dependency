package org.zerofruit.ecommerce.service.store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.domain.store.Product;
import org.zerofruit.ecommerce.domain.store.ProductRepository;
import org.zerofruit.ecommerce.domain.store.Store;
import org.zerofruit.ecommerce.domain.store.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    private StoreRepository storeRepository;
    private ProductRepository productRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public ProductBoard getProductBoard(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(IllegalArgumentException::new);
        List<Product> products = productRepository.findByStoreId(storeId);

        return new ProductBoard(store, products);
    }
}
