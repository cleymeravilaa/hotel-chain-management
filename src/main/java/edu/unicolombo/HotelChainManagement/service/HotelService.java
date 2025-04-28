package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
