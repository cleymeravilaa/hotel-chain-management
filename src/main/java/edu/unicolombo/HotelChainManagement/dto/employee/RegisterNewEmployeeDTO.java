package edu.unicolombo.HotelChainManagement.dto.employee;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;

public record RegisterNewEmployeeDTO(Long hotelId, String dni, String name, String address, EmployeeType type) {

    public RegisterNewEmployeeDTO(Employee employee){
        this(employee.getHotel().getHotelId(), employee.getDni(), employee.getName(), 
        employee.getAddress(), employee.getType());
    }
}
