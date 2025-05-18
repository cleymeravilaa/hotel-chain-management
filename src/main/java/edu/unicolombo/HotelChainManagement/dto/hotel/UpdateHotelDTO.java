package edu.unicolombo.HotelChainManagement.dto.hotel;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

public record UpdateHotelDTO(long hotelId, String name, Integer category, String address, String phone, Long directorId) {
    public UpdateHotelDTO(Hotel hotel){
        this(hotel.getHotelId(), hotel.getName(), hotel.getCategory(), hotel.getAddress(), hotel.getPhone(), hotel.getDirector().getEmployeeId());
    }
}
    