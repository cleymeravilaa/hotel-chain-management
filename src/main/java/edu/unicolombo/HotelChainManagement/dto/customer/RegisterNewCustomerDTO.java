package edu.unicolombo.HotelChainManagement.dto.customer;

import edu.unicolombo.HotelChainManagement.domain.model.Customer;

public record RegisterNewCustomerDTO(String dni, String name, String address, String phone) {

    public RegisterNewCustomerDTO(Customer customer) {
        this(customer.getDni(), customer.getName(), customer.getAddress(), customer.getPhone());
    }
}
