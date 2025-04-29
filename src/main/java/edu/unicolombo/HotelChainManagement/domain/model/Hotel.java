package edu.unicolombo.HotelChainManagement.domain.model;

import edu.unicolombo.HotelChainManagement.dto.hotel.RegisterNewHotelDTO;
import edu.unicolombo.HotelChainManagement.dto.hotel.UpdateHotelDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "Hotel")
@Table(name = "hotels")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "hotelId")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;
    private String name;
    private Integer category; // starts
    private String address;
    private String phone;
    // Relacion 1:1 con empleado (director)
    @OneToOne
    @JoinColumn(name = "director", referencedColumnName = "employeeId")
    private Employee director;

    public Hotel(String name, int category, String address, String phone) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.phone = phone;
    }

    public Hotel(RegisterNewHotelDTO data){
        this.name = data.name();
        this.category = data.category();
        this.address = data.address();
        this.phone = data.phone();
    }

    public void updateData(UpdateHotelDTO data) {

    }
}
