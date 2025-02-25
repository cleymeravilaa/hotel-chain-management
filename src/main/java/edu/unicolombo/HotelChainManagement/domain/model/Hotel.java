package edu.unicolombo.HotelChainManagement.domain.model;

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
    private int starsNumber;
    private String address;
    private String phone;
    @OneToOne
    @JoinColumn(name = "director")
    private Employee director;

    public Hotel(String name, int numberStars, String address, String phone) {
        this.name = name;
        this.starsNumber = numberStars;
        this.address = address;
        this.phone = phone;
    }
}
