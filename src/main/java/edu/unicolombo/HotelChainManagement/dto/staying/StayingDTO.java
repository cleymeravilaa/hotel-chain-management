package edu.unicolombo.HotelChainManagement.dto.staying;

import java.time.LocalDate;
import java.util.List;

import edu.unicolombo.HotelChainManagement.domain.model.Staying;
import edu.unicolombo.HotelChainManagement.dto.invoice.InvoiceDTO;

public record StayingDTO(Long stayingId, LocalDate startDate, LocalDate endDate, 
                        Long bookingId, List<StayingRoomDTO> stayingRooms, InvoiceDTO invoice) {

    public StayingDTO(Staying staying){
        this(staying.getStayingId(), staying.getStartDate(), 
        staying.getEndDate(), staying.getBooking().getBookingId(), 
        staying.getStayingRoom().stream().map(
            s -> new StayingRoomDTO(s)
        ).toList(), new InvoiceDTO(staying.getInvoice()));
    }
}
