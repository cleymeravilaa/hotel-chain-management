package edu.unicolombo.HotelChainManagement.service;

import edu.unicolombo.HotelChainManagement.domain.model.Customer;
import edu.unicolombo.HotelChainManagement.domain.repository.CustomerRepository;
import edu.unicolombo.HotelChainManagement.dto.customer.CustomerDTO;
import edu.unicolombo.HotelChainManagement.dto.customer.RegisterNewCustomerDTO;
import edu.unicolombo.HotelChainManagement.dto.customer.UpdateCustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    public CustomerRepository customerRepository;

    public Customer registerCustomer(RegisterNewCustomerDTO data) {
        var customer = new Customer(data);
        return customerRepository.save(customer);
    }

    public Customer findById(long id) { return customerRepository.getReferenceById(id);}

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(long customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);

        return new CustomerDTO(customer);
    }

    public void deleteById(long customerId) {customerRepository.deleteById(customerId);}

    public CustomerDTO updateCustomer(long customerId, UpdateCustomerDTO data) {
        Customer customer = customerRepository.getReferenceById(customerId);
        customer.updateData(data);
        return new CustomerDTO(customerRepository.save(customer));
    }
}
