package edu.unicolombo.HotelChainManagement.dto.employee;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;

public record UpdateEmployeeDTO(Long employeeId, String name, String address, EmployeeType type) {

    public UpdateEmployeeDTO(Employee employee){
        this(employee.getEmployeeId(), employee.getName(), employee.getAddress(), employee.getType());
    }
}
