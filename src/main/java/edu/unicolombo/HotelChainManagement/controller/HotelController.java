package edu.unicolombo.HotelChainManagement.controller;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.hotel.HotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import edu.unicolombo.HotelChainManagement.service.EmployeeService;
import edu.unicolombo.HotelChainManagement.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {

    @Autowired
    public HotelRepository hotelRepository;
    @Autowired
    public HotelService hotelService;

    @Autowired
    public EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<HotelDTO> registerHotel(@RequestBody RegisterNewHotelDTO data, UriComponentsBuilder uriBuilder){
        var registeredHotel = hotelService.register(data);
        var director =  employeeRepository.getReferenceById(data.directorId());
        director.setHotel(registeredHotel);
        employeeRepository.save(director);
        URI url = uriBuilder.path("/hotels/{hotelId}").buildAndExpand(registeredHotel.getHotelId()).toUri();
        return ResponseEntity.created(url).body(new HotelDTO(registeredHotel));
    }
}
