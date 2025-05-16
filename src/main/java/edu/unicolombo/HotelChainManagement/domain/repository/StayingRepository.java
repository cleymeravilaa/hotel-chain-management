package edu.unicolombo.HotelChainManagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unicolombo.HotelChainManagement.domain.model.Staying;

public interface StayingRepository extends JpaRepository<Staying, Long> {

}
