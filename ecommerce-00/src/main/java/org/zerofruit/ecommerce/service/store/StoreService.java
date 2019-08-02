package org.zerofruit.ecommerce.service.store;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerofruit.ecommerce.domain.store.Store;
import org.zerofruit.ecommerce.domain.store.StoreRepository;

@Service
public class StoreService {
    private StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public ProductBoard getProductBoard(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(IllegalArgumentException::new);

        return new ProductBoard(store);
    }
}
