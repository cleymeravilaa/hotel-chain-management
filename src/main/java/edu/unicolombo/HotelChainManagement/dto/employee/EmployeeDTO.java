package edu.unicolombo.HotelChainManagement.dto.employee;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;

public record EmployeeDTO(long employeeId, String dni, String name,
                          String address, EmployeeType type) {

    public EmployeeDTO(Employee employee){
        this(employee.getEmployeeId(), employee.getDni(),
                employee.getName(), employee.getAddress(),
                employee.getType());
    }
}
