package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.repository.EmployeeRepository;
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

    public Employee registerEmployee(RegisterNewEmployeeDTO data){
        var employee = new Employee(data);
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id){
        return employeeRepository.getReferenceById(id);
    }

    public List<EmployeeDTO> getAllEmployees(){
        return employeeRepository.findAll()
                .stream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(long employeeId) {
        Employee employee =  employeeRepository.getReferenceById(employeeId);

        return new EmployeeDTO(employee);
    }

    public void deleteById(long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public EmployeeDTO updateEmployee(UpdateEmployeeDTO data){
        Employee  employee = employeeRepository.getReferenceById(data.employeeId());
        employee.updateData(data);

        return new EmployeeDTO(employeeRepository.save(employee));
    }
}
