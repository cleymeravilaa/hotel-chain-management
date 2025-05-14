package edu.unicolombo.HotelChainManagement.dto.booking;

import java.time.LocalDate;
import java.util.List;

import edu.unicolombo.HotelChainManagement.domain.model.Booking;
import edu.unicolombo.HotelChainManagement.dto.room.RoomDTO;

public record BookingDTO(Long bookingId, Long customerId, Long hotelId, List<RoomDTO> rooms, 
            LocalDate startDate, LocalDate endDate, Double advanceDeposit) {

    public BookingDTO(Booking booking){
        this(booking.getBookingId(), booking.getCustomer().getCustomerId(), 
                booking.getHotel().getHotelId(), 
                booking.getRooms().stream().map(RoomDTO::new).toList(), booking.getStartDate()
                , booking.getEndDate(), booking.getAdvanceDeposit());
    }

}
