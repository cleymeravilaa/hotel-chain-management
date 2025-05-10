package edu.unicolombo.HotelChainManagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.unicolombo.HotelChainManagement.dto.room.RegisterNewRoomDTO;
import edu.unicolombo.HotelChainManagement.dto.room.RoomDTO;
import edu.unicolombo.HotelChainManagement.dto.room.UpdateRoomDTO;
import edu.unicolombo.HotelChainManagement.service.RoomService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    public RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> registerRoom(@RequestBody RegisterNewRoomDTO data, UriComponentsBuilder uriBuilder){
        var registeredRoom = roomService.registerRoom(data);

        URI url = uriBuilder.path("/rooms/{roomId}").buildAndExpand(registeredRoom.getRoomId()).toUri();
        return ResponseEntity.created(url).body(new RoomDTO(registeredRoom));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long roomId){
        return ResponseEntity.ok(new RoomDTO(roomService.getRoomById(roomId)));
    }

    @DeleteMapping("/{roomId}")
    @Transactional
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteById(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    @Transactional
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable long roomId, @RequestBody UpdateRoomDTO data){
        return ResponseEntity.ok(roomService.updateRoom(roomId, data));
    }
}
