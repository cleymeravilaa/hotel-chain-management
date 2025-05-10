package edu.unicolombo.HotelChainManagement.dto.room;

import edu.unicolombo.HotelChainManagement.domain.model.RoomType;
import edu.unicolombo.HotelChainManagement.domain.model.RoomStatus;

public record UpdateRoomDTO(RoomType type, Double basePrice, RoomStatus status) {

}
