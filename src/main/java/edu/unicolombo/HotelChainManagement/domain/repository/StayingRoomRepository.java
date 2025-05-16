package edu.unicolombo.HotelChainManagement.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.unicolombo.HotelChainManagement.domain.model.StayingRoom;
import edu.unicolombo.HotelChainManagement.domain.model.StayingRoomId;

public interface StayingRoomRepository extends JpaRepository<StayingRoom, StayingRoomId> {

}
