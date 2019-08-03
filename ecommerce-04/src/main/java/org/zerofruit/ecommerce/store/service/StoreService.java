package org.zerofruit.ecommerce.store.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.store.domain.Product;
import org.zerofruit.ecommerce.store.domain.ProductRepository;
import org.zerofruit.ecommerce.store.domain.Store;
import org.zerofruit.ecommerce.store.domain.StoreRepository;

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
