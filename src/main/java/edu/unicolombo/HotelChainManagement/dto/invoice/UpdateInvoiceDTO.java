package edu.unicolombo.HotelChainManagement.dto.invoice;

import edu.unicolombo.HotelChainManagement.domain.model.Invoice;
import edu.unicolombo.HotelChainManagement.domain.model.Staying;

import java.time.LocalDateTime;

public record UpdateInvoiceDTO(long invoiceId, Staying stay, LocalDateTime issueDate, int totalOfRooms, Double finalTotal) {

    public UpdateInvoiceDTO(Invoice invoice) {
        this(invoice.getInvoiceId(),invoice.getStay(), invoice.getIssueDate(), invoice.getTotalOfRooms(), invoice.getFinalTotal());
    }
}
