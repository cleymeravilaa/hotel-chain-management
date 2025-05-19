package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;
import edu.unicolombo.HotelChainManagement.domain.model.Hotel;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.employee.EmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.RegisterNewEmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.UpdateEmployeeDTO;
import edu.unicolombo.HotelChainManagement.infrastructure.errors.exception.BusinessLogicValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public HotelRepository hotelRepository;

    public EmployeeDTO registerEmployee(RegisterNewEmployeeDTO data){
        Hotel hotel = hotelRepository.findById(data.hotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        Employee employee = new Employee();
        employee.setName(data.name());
        employee.setAddress(data.address());
        employee.setType(data.type());
        employee.setHotel(hotel);
        employee.setDni(data.dni());

        // Lógica específica para directores
        if (data.type() == EmployeeType.DIRECTOR) {
            if (hotel.getDirector() != null) {
                throw new BusinessLogicValidationException("El hotel ya tiene un director asignado");
            }
            hotel.setDirector(employee);
        } else {
            hotel.getEmployees().add(employee);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee);
    }

    public Employee findById(Long id){
        return employeeRepository.getReferenceById(id);
    }

    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream().map(EmployeeDTO::new
                ).toList();
    }

    public List<EmployeeDTO> getAllEmployeesByHotel(Long hotelId){
        var hotel = hotelRepository.getReferenceById(hotelId);
        return employeeRepository.findByHotel(hotel)
                .stream().map(EmployeeDTO::new).toList();
    }

    public EmployeeDTO getEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(EmployeeDTO::new).orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
    }

    public void deleteById(long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        Hotel hotel = employee.getHotel();

        if (employee.getType() == EmployeeType.DIRECTOR) {
            // Si es director, eliminamos la referencia
            hotel.setDirector(null);
        } else {
            // Si es empleado normal, lo removemos de la lista
            hotel.getEmployees().remove(employee);
        }
        employeeRepository.delete(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(long employeeId, UpdateEmployeeDTO data){
        Employee  employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        // 1. Manejo de cambio de hotel si es necesario
        handleHotelChange(employee, data);

        // 2. Manejar cambio de tipo (rol) si es necesario
        handleTypeChange(employee, data);

        // 3. Actualizar datos básicos
        employee.setName(data.name());
        employee.setAddress(data.address());

        // 4. Guardar cambios
        Employee updatedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(updatedEmployee);
    }

    private void handleHotelChange(Employee employee, UpdateEmployeeDTO data) {
        if (data.hotelId() == null || data.hotelId().equals(employee.getHotel().getHotelId())) {
            return; // No hay cambio de hotel
        }

        Hotel newHotel = hotelRepository.findById(data.hotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel no encontrado"));

        // Remover empleado del hotel actual
        if (employee.getHotel() != null) {
            employee.getHotel().getEmployees().remove(employee);
            if (employee.getType() == EmployeeType.DIRECTOR) {
                employee.getHotel().setDirector(null);
            }
        }

        // Asignar al nuevo hotel
        employee.setHotel(newHotel);
        newHotel.getEmployees().add(employee);

        // Si es director, asignar como director del nuevo hotel
        if (employee.getType() == EmployeeType.DIRECTOR) {
            if (newHotel.getDirector() != null) {
                throw new BusinessLogicValidationException("El hotel ya tiene un director");
            }
            newHotel.setDirector(employee);
        }
    }

    private void handleTypeChange(Employee employee, UpdateEmployeeDTO data) {
        if (data.type() == employee.getType()) {
            return; // No hay cambio de tipo
        }

        // Cambio de DIRECTOR a otro rol
        if (employee.getType() == EmployeeType.DIRECTOR) {
            Hotel currentHotel = employee.getHotel();
            currentHotel.setDirector(null);
            // El empleado sigue en la lista de empleados del hotel
        }

        // Cambio a DIRECTOR desde otro rol
        if (data.type() == EmployeeType.DIRECTOR) {
            Hotel currentHotel = employee.getHotel();
            if (currentHotel.getDirector() != null) {
                throw new BusinessLogicValidationException("El hotel ya tiene un director");
            }
            currentHotel.setDirector(employee);
            currentHotel.getEmployees().remove(employee); // Director no está en la lista de empleados
        }

        employee.setType(data.type());
    }
}
