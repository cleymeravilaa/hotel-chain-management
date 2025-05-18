package edu.unicolombo.HotelChainManagement.domain.model;

import java.util.List;

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
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;
    // Relacion 1:1 con empleado (director)
    @OneToOne
    @JoinColumn(name = "director")
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

        if(data.name() != null) {
            this.name = data.name();
        }

        if(data.category() != null) {
            this.category = data.category();
        }

        if(data.address() != null) {
            this.address = data.address();
        }

        if(data.phone() != null) {
            this.phone = data.phone();
        }
    }
}
