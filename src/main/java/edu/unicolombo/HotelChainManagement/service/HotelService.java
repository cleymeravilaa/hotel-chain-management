package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.hotel.HotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.UpdateHotelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    public HotelRepository hotelRepository;

    @Autowired
    public EmployeeRepository employeeRepository;

    public Hotel register(RegisterNewHotelDTO data){
        var director = employeeRepository.getReferenceById(data.directorId());
        var hotel = new Hotel(data);
        hotel.setDirector(director);
        employeeRepository.save(director);
        return hotelRepository.save(hotel);
    }

    public Hotel findById(long id) {
        return hotelRepository.getReferenceById(id);
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream().map(HotelDTO::new).collect(Collectors.toList());
    }

    public HotelDTO getHotelById(long hotelId){
        Hotel hotel = hotelRepository.getReferenceById(hotelId);

        return new HotelDTO(hotel);
    }

    public void deleteById(long hotelId) {
        hotelRepository.deleteById(hotelId);
    }

    public HotelDTO updateHotel(Long hotelId, UpdateHotelDTO data) {
        Hotel hotel = hotelRepository.getReferenceById(hotelId);
        hotel.updateData(data);

        return new HotelDTO(hotelRepository.save(hotel));
    }
}
