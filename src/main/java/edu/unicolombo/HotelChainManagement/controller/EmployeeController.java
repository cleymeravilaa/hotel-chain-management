package edu.unicolombo.HotelChainManagement.controller;

import edu.unicolombo.HotelChainManagement.dto.employee.EmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.RegisterNewEmployeeDTO;
import edu.unicolombo.HotelChainManagement.dto.employee.UpdateEmployeeDTO;
import edu.unicolombo.HotelChainManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    public EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDTO> registerEmployee(@RequestBody RegisterNewEmployeeDTO data, UriComponentsBuilder uriBuilder){
        var registeredEmployee = employeeService.registerEmployee(data);
        URI url = uriBuilder.path("/employees/{employeeId}").buildAndExpand(registeredEmployee.getEmployeeId()).toUri();
        return ResponseEntity.created(url).body(new EmployeeDTO(registeredEmployee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable long employeeId){
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @DeleteMapping("/{employeeId}")
    @Transactional
    public ResponseEntity<Void> deleteEmployee(@PathVariable long employeeId){
        employeeService.deleteById(employeeId);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{employeeId}")
    @Transactional
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable long employeeId,@RequestBody UpdateEmployeeDTO data){
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, data));
    }
}
