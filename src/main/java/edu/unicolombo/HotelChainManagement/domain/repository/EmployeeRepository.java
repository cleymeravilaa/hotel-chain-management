package edu.unicolombo.HotelChainManagement.domain.repository;

import edu.unicolombo.HotelChainManagement.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
