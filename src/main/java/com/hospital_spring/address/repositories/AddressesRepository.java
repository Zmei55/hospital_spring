package com.hospital_spring.address.repositories;

import com.hospital_spring.address.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressesRepository extends JpaRepository<Address, Long> {
}
