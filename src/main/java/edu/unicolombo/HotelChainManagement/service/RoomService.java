package edu.unicolombo.HotelChainManagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.repository.RoomRepository;
import edu.unicolombo.HotelChainManagement.dto.room.RegisterNewRoomDTO;
import edu.unicolombo.HotelChainManagement.dto.room.RoomDTO;
import edu.unicolombo.HotelChainManagement.dto.room.UpdateRoomDTO;

@Service
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

    public Room registerRoom(RegisterNewRoomDTO data){
        var room = new Room(data);
        return roomRepository.save(room);
    }

    public Room getRoomById(Long id){
        return roomRepository.getReferenceById(id);
    }

    public List<RoomDTO> getAllRooms(){
        return roomRepository.findAll()
                    .stream().map(RoomDTO::new).collect(Collectors.toList());
    }

    public void deleteById(Long roomId){
        roomRepository.deleteById(roomId);
    }

    public RoomDTO updateRoom(UpdateRoomDTO data){
        Room room = roomRepository.getReferenceById(data.roomId());
        room.updateData(data);
        return new RoomDTO(roomRepository.save(room));
    }

}
