package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;
import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.hotel.HotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.UpdateHotelDTO;
import edu.unicolombo.HotelChainManagement.infrastructure.errors.exception.BusinessLogicValidationException;
import jakarta.persistence.EntityNotFoundException;
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

    public HotelDTO register(RegisterNewHotelDTO data){
        Hotel hotel = new Hotel();
        hotel.setName(data.name());
        hotel.setAddress(data.address());
        hotel.setPhone(data.phone());
        hotel.setCategory(data.category());

        // El director se asigna después de crear el hotel si se proporciona
        Hotel savedHotel = hotelRepository.save(hotel);
//
//        if (hotelDTO.directorId() != null) {
//            assignDirector(savedHotel.getId(), hotelDTO.directorId());
//        }

        return new HotelDTO(savedHotel);
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelDTO::new)
                .toList();
    }

    public HotelDTO getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(HotelDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));
    }

    @Transactional
    public void deleteById(long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        // Primero desvincular a todos los empleados
        hotel.getEmployees().forEach(employee -> employee.setHotel(null));
        if (hotel.getDirector() != null) {
            hotel.getDirector().setHotel(null);
        }
        hotelRepository.delete(hotel);
    }

    public HotelDTO updateHotel(Long hotelId, UpdateHotelDTO data) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        hotel.setName(data.name());
        hotel.setAddress(data.address());
        hotel.setPhone(data.phone());
        hotel.setCategory(data.category());

        // Manejo especial para el director si cambia
        if (data.directorId() != null &&
                (hotel.getDirector() == null || !hotel.getDirector().getEmployeeId().equals(data.directorId()))) {
            assignDirector(hotelId, data.directorId());
        } else if (data.directorId() == null && hotel.getDirector() != null) {
            removeDirector(hotelId);
        }
        Hotel updatedHotel = hotelRepository.save(hotel);
        return new HotelDTO(updatedHotel);
    }

    public void assignDirector(Long hotelId, Long employeeId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // Validar que el empleado no sea director en otro hotel
        if (employee.getType() == EmployeeType.DIRECTOR &&
                employee.getHotel() != null &&
                !employee.getHotel().getHotelId().equals(hotelId)) {
            throw new BusinessLogicValidationException("Este empleado ya es director de otro hotel");
        }

        // Si el hotel ya tiene director, desvincularlo primero
        if (hotel.getDirector() != null) {
            removeDirector(hotelId);
        }

        // Actualizar relaciones bidireccionales
        employee.setType(EmployeeType.DIRECTOR);
        employee.setHotel(hotel);
        hotel.setDirector(employee);

        // Remover de la lista de empleados si estaba allí
        hotel.getEmployees().remove(employee);

        hotelRepository.save(hotel);
        employeeRepository.save(employee);
    }

    public void removeDirector(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        if (hotel.getDirector() == null) {
            return;
        }

        Employee director = hotel.getDirector();
        director.setType(EmployeeType.CLEANING); // Cambiar a otro rol
        hotel.setDirector(null);

        // Agregar a la lista general de empleados
        hotel.getEmployees().add(director);

        hotelRepository.save(hotel);
        employeeRepository.save(director);
    }
}
