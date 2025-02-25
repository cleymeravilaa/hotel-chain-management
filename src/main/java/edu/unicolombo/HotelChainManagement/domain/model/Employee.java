package edu.unicolombo.HotelChainManagement.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Employee")
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private String address;
    private String type;
    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;

    public Employee(String dni, String name, String address, String type) {
        this.dni= dni;
        this.name = name;
        this.address = address;
        this.type = type;
    }

}
