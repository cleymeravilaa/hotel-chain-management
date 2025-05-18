package edu.unicolombo.HotelChainManagement.dto.hotel;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

public record HotelDTO(long hotelId, String name, int category, String address, String phone, Long directorId) {

    public HotelDTO(Hotel hotel){
        this(hotel.getHotelId(), hotel.getName(), hotel.getCategory(), hotel.getAddress(),
                hotel.getPhone(), hotel.getDirector()==null ? null : hotel.getDirector().getEmployeeId());
    }
}
