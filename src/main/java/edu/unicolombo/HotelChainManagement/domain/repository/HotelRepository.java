package edu.unicolombo.HotelChainManagement.domain.repository;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository  extends JpaRepository<Hotel, Long> {
}
