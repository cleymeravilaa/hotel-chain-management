package edu.unicolombo.HotelChainManagement.dto.invoice;

import edu.unicolombo.HotelChainManagement.domain.model.Invoice;
import edu.unicolombo.HotelChainManagement.domain.model.Staying;

import java.time.LocalDateTime;

public record InvoiceDTO(Staying stay, LocalDateTime issueDate, int totalOfRooms, Double finalTotal) {
    public InvoiceDTO(Invoice invoice) {
        this(invoice.getStay(), invoice.getIssueDate(), invoice.getTotalOfRooms(), invoice.getFinalTotal());
    }
}
