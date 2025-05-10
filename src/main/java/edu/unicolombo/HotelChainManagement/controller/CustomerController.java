package edu.unicolombo.HotelChainManagement.controller;

import edu.unicolombo.HotelChainManagement.dto.customer.CustomerDTO;
import edu.unicolombo.HotelChainManagement.dto.customer.RegisterNewCustomerDTO;
import edu.unicolombo.HotelChainManagement.dto.customer.UpdateCustomerDTO;
import edu.unicolombo.HotelChainManagement.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> registerCustomers(@RequestBody RegisterNewCustomerDTO data, UriComponentsBuilder uribuilder) {
        var registeredCustomers = customerService.registerCustomer(data);
        URI url = uribuilder.path("/customer/{customerId}").buildAndExpand(registeredCustomers.getCustomerId()).toUri();
        return ResponseEntity.created(url).body(new CustomerDTO(registeredCustomers));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @DeleteMapping("/{customerId}")
    @Transactional
    public ResponseEntity<Void> deleteCustomer(@PathVariable long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{customerId}")
    @Transactional
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable long customerId, @RequestBody UpdateCustomerDTO data) {
        return ResponseEntity.ok(customerService.updateCustomer(customerId, data));
    }
}
