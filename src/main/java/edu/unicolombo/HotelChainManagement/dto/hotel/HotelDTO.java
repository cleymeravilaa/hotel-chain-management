package edu.unicolombo.HotelChainManagement.dto.hotel;

import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

public record HotelDTO(long hotelId, String name, int numberStars, String address, String phone, long directorId) {

    public HotelDTO(Hotel hotel){
        this(hotel.getHotelId(), hotel.getName(), hotel.getCategory(), hotel.getAddress(),
                hotel.getPhone(), hotel.getDirector().getEmployeeId());
    }
}
