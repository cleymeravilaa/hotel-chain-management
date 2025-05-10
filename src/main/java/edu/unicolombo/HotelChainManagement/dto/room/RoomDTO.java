package edu.unicolombo.HotelChainManagement.dto.room;

import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.model.RoomType;
import edu.unicolombo.HotelChainManagement.domain.model.RoomStatus;


public record RoomDTO(Long roomId, Long hotelId, RoomType type, Double basePrice, RoomStatus status) {

    public RoomDTO(Room room){
        this(room.getRoomId(), room.getHotel().getHotelId(), room.getType(), room.getBasePrice(), room.getStatus());
    }
}
