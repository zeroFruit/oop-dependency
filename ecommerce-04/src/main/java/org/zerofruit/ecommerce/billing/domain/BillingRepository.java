package org.zerofruit.ecommerce.billing.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {
    Optional<Billing> findByStoreId(Long shopId);
}
