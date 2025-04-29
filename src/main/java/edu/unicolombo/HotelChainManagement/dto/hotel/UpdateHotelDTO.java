package edu.unicolombo.HotelChainManagement.dto.hotel;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

public record UpdateHotelDTO(long hotelId, String name, String address) {
    public UpdateHotelDTO(Hotel hotel){
        this(hotel.getHotelId(), hotel.getName(), hotel.getAddress());
    }
}
