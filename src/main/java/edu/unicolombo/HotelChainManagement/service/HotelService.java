package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.hotel.HotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.UpdateHotelDTO;
import jakarta.transaction.Transactional;
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
        var hotel = new Hotel(data);
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

    @Transactional
    public void deleteById(long hotelId) {
        var hotel = hotelRepository.getReferenceById(hotelId);

        // 1. Desvincula empleados normales
        hotel.getEmployees().forEach(e -> e.setHotel(null));

        // 2. Desvincula director si existe
        if(hotel.getDirector() != null){
            hotel.getDirector().setManagedHotel(null);
            hotel.setDirector(null);
        }

        // 3. Eliminar Hotel
        hotelRepository.delete(hotel);
    }

    public HotelDTO updateHotel(Long hotelId, UpdateHotelDTO data) {
        Hotel hotel = hotelRepository.getReferenceById(hotelId);

        if (data.directorId()!=null){
            var director = employeeRepository.getReferenceById(data.directorId());
            hotel.setDirector(director);
        }
        hotel.updateData(data);

        return new HotelDTO(hotelRepository.save(hotel));
    }
}
