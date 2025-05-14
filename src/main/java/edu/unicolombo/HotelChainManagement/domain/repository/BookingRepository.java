package edu.unicolombo.HotelChainManagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.unicolombo.HotelChainManagement.domain.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
