package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.dto.employee.EmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.RegisterNewEmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.UpdateEmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    public EmployeeRepository employeeRepository;

    @Autowired
    public HotelRepository hotelRepository;

    public Employee registerEmployee(RegisterNewEmployeeDTO data){
        var employee = new Employee(data);
        if(data.hotelId()!=null){
            var hotel = hotelRepository.getReferenceById(data.hotelId());
            hotel.getEmployees().add(employee);
            employee.setHotel(hotel);
        }
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id){
        return employeeRepository.getReferenceById(id);
    }

    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public List<EmployeeDTO> getAllEmployeesByHotel(Long hotelId){
        var hotel = hotelRepository.getReferenceById(hotelId);
        return employeeRepository.findByHotel(hotel).stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(long employeeId) {
        Employee employee =  employeeRepository.getReferenceById(employeeId);

        return new EmployeeDTO(employee);
    }

    public void deleteById(long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeDTO updateEmployee(long employeeId, UpdateEmployeeDTO data){
        Employee  employee = employeeRepository.getReferenceById(employeeId);
        employee.updateData(data);
        if (data.hotelId()!=null) {
            var hotel = hotelRepository.getReferenceById(data.hotelId());
            employee.setHotel(hotel);
        }
        var employeeSaved = employeeRepository.save(employee);
        return new EmployeeDTO(employeeSaved);
    }
}
