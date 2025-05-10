package edu.unicolombo.HotelChainManagement.dto.employee;

import edu.unicolombo.HotelChainManagement.domain.model.EmployeeType;

public record UpdateEmployeeDTO(Long hotelId, String name, String address, EmployeeType type) {

}
