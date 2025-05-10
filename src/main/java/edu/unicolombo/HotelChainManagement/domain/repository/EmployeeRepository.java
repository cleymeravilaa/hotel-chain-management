package edu.unicolombo.HotelChainManagement.domain.repository;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import edu.unicolombo.HotelChainManagement.domain.model.Hotel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByHotel(Hotel hotel);
}
