package edu.unicolombo.HotelChainManagement.dto.booking;

import java.time.LocalDate;
import java.util.List;

public record UpdateBookingDTO(Long hotelId, LocalDate startDate, LocalDate endDate, List<Long> roomIds) {

}
