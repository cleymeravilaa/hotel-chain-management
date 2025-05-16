package edu.unicolombo.HotelChainManagement.dto.invoice;

import edu.unicolombo.HotelChainManagement.domain.model.Invoice;

import java.time.LocalDateTime;

public record RegisterNewInvoiceDTO(Long stayingId, LocalDateTime issueDate, int totalOfRooms, Double finalTotal) {

    public RegisterNewInvoiceDTO(Invoice invoice) {
        this(invoice.getStaying().getStayingId(), invoice.getIssueDate(), invoice.getTotalRooms(), invoice.getFinalTotal());
    }
}
