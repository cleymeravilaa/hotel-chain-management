package edu.unicolombo.HotelChainManagement.dto.hotel;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

public record RegisterNewHotelDTO(String name, Integer category, String address, String phone, Long directorId) {

    public RegisterNewHotelDTO(Hotel hotel){
        this(hotel.getName(), hotel.getCategory(), hotel.getAddress(),hotel.getPhone(), hotel.getDirector().getEmployeeId());
    }
}
