package edu.unicolombo.HotelChainManagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.unicolombo.HotelChainManagement.dto.staying.StayingDTO;
import edu.unicolombo.HotelChainManagement.dto.staying.UpdateStayingDTO;
import edu.unicolombo.HotelChainManagement.service.StayingService;

@RestController
@RequestMapping("/api/v1/stayings")
public class StayingController {

    @Autowired
    public StayingService stayingService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<StayingDTO> toCheckIn(@PathVariable Long bookingId, UriComponentsBuilder uriBuilder){
        var registeredStaying = stayingService.toCheckIn(bookingId);
        URI url = uriBuilder.path("/stayings/{stayingId}").buildAndExpand(registeredStaying.stayingId()).toUri();
        return ResponseEntity.created(url).body(registeredStaying);
    }

    @GetMapping
    public ResponseEntity<List<StayingDTO>> getAllStayings(){
        return ResponseEntity.ok(stayingService.getAllStayings());
    }

    @GetMapping("/{stayingId}")
    public ResponseEntity<StayingDTO> getStayingById(@PathVariable Long stayingId){
        var staying = stayingService.getStayingById(stayingId);
        return ResponseEntity.ok(staying);
    }

    @PutMapping("/{stayingId}")
    public ResponseEntity<StayingDTO> toCheckOut(@PathVariable Long stayingId, @RequestBody UpdateStayingDTO data){
        return stayingService.toCheckOutRooms(stayingId, data);
    }

}
