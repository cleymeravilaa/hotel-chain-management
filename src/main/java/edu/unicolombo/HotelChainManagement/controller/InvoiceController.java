package edu.unicolombo.HotelChainManagement.controller;

import edu.unicolombo.HotelChainManagement.dto.invoice.InvoiceDTO;
import edu.unicolombo.HotelChainManagement.dto.invoice.RegisterNewInvoiceDTO;
import edu.unicolombo.HotelChainManagement.dto.invoice.UpdateInvoiceDTO;
import edu.unicolombo.HotelChainManagement.service.InvoiceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    public InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDTO> RegisterInvoices(@RequestBody RegisterNewInvoiceDTO data, UriComponentsBuilder uriBuilder) {
        var registeredInvoices = invoiceService.registerInvoice(data);
        URI url = uriBuilder.path("/invoice/{invoiceId}").buildAndExpand(registeredInvoices.getInvoiceId()).toUri();

        return ResponseEntity.created(url).body(new InvoiceDTO(registeredInvoices));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable long invoiceId) {
        return ResponseEntity.ok(invoiceService.getInvoicesById(invoiceId));
    }

    @DeleteMapping("/{invoiceId}")
    @Transactional
    public ResponseEntity<Void> deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.deleteById(invoiceId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable long invoiceId, @RequestBody UpdateInvoiceDTO data) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoiceId,data));
    }
}
