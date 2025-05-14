package edu.unicolombo.HotelChainManagement.domain.repository;

import edu.unicolombo.HotelChainManagement.domain.model.Room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotel_hotelIdAndRoomIdIn(Long hotelId, List<Long> ids);
}
