package edu.unicolombo.HotelChainManagement.dto.room;

import edu.unicolombo.HotelChainManagement.domain.model.RoomType;
import edu.unicolombo.HotelChainManagement.domain.model.Room;


public record RegisterNewRoomDTO(long hotelId,  RoomType type, Double basePrice) {

    public RegisterNewRoomDTO(Room room){
        this(room.getHotel().getHotelId(), room.getType(), room.getBasePrice());
    }
}
