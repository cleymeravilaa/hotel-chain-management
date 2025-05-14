package edu.unicolombo.HotelChainManagement.dto.booking;

import java.time.LocalDate;
import java.util.List;

public record RegisterBookingDTO(Long customerId, Long hotelId, List<Long> roomIds
                                , LocalDate startDate, LocalDate endDate) {

        

}
