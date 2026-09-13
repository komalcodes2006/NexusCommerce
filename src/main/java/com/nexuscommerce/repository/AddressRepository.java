package com.nexuscommerce.repository;

import com.nexuscommerce.entity.Address;
import com.nexuscommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
}