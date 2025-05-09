package edu.unicolombo.HotelChainManagement.dto.room;

import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.model.RoomType;

public record RoomDTO(Long roomId, Long hotelId, RoomType type, Double basePrice) {

    public RoomDTO(Room room){
        this(room.getRoomId(), room.getHotel().getHotelId(), room.getType(), room.getBasePrice());
    }
}
