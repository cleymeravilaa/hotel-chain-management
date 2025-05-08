package edu.unicolombo.HotelChainManagement.dto.customer;

import edu.unicolombo.HotelChainManagement.domain.model.Customer;

public record UpdateCustomerDTO(long customerId, String name, String address, String phone) {

    public UpdateCustomerDTO(Customer customer) {
        this(customer.getId(), customer.getName(),
                customer.getAddress(), customer.getPhone());
    }
}
