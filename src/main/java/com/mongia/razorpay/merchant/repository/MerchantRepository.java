package com.mongia.razorpay.merchant.repository;

import com.mongia.razorpay.merchant.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {

    Boolean existsByEmail(String email);
}
