package edu.unicolombo.HotelChainManagement.dto.room;

import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.model.RoomType;

public record UpdateRoomDTO(Long roomId, RoomType type, Double basePrice) {

    public UpdateRoomDTO(Room room){
        this(room.getRoomId(), room.getType(), room.getBasePrice());
    }
}
