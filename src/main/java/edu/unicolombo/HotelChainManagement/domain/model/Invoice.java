package edu.unicolombo.HotelChainManagement.domain.model;

import edu.unicolombo.HotelChainManagement.dto.invoice.RegisterNewInvoiceDTO;
import edu.unicolombo.HotelChainManagement.dto.invoice.UpdateInvoiceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Invoice")
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "invoiceId")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;
    private LocalDateTime issueDate;
    private int totalOfRooms;
    private Double finalTotal;
    @JoinColumn(name = "stay")
    private Staying stay;

    public Invoice(Staying stay, LocalDateTime issueDate, int totalOfRooms, Double finalTotal) {
        this.stay = stay;
        this.issueDate = issueDate;
        this.totalOfRooms = totalOfRooms;
        this.finalTotal = finalTotal;
    }

    public Invoice(RegisterNewInvoiceDTO data) {
        this.stay = data.stay();
        this.issueDate = data.issueDate();
        this.totalOfRooms = data.totalOfRooms();
        this.finalTotal = data.finalTotal();
    }

    public void updateData(UpdateInvoiceDTO data) {
        if (data.stay() != null) {
            this.stay = data.stay();
        }

        if(data.issueDate() != null) {
            this.issueDate = data.issueDate();
        }

        if(data.totalOfRooms() != 0) {
            this.totalOfRooms = data.totalOfRooms();
        }

        if (data.finalTotal() != 0) {
            this.finalTotal = data.finalTotal();
        }
    }
}
