package edu.unicolombo.HotelChainManagement.dto.employee;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;
import edu.unicolombo.HotelChainManagement.domain.model.RoomType;

public record EmployeeDTO(long employeeId, String dni, String name,
                          String address, EmployeeType type, Long hotelId) {

    public EmployeeDTO(Employee employee){
        this(
                employee.getEmployeeId(),
                employee.getDni(),
                employee.getName(),
                employee.getAddress(),
                employee.getType(),
                employee.getHotel()!=null ? employee.getHotel().getHotelId() : null
        );
    }
}
