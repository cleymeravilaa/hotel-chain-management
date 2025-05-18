package edu.unicolombo.HotelChainManagement.dto.invoice;

import edu.unicolombo.HotelChainManagement.domain.model.Invoice;

import java.time.LocalDateTime;

public record UpdateInvoiceDTO(LocalDateTime issueDate, int totalOfRooms, Double finalTotal) {

    public UpdateInvoiceDTO(Invoice invoice) {
        this(invoice.getIssueDate(), invoice.getTotalRooms(), invoice.getFinalTotal());
    }
}
