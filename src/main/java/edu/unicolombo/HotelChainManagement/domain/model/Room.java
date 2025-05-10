package edu.unicolombo.HotelChainManagement.domain.model;

import edu.unicolombo.HotelChainManagement.dto.room.RegisterNewRoomDTO;
import edu.unicolombo.HotelChainManagement.dto.room.UpdateRoomDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rooms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "roomId")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    @ManyToOne
    @JoinColumn(name = "hotel")
    private Hotel hotel;
    @Enumerated(EnumType.STRING)
    private RoomType type;
    private Double basePrice;
    @Enumerated(EnumType.STRING)
    private RoomStatus status;


    public Room(RegisterNewRoomDTO data) {
        this.type = data.type();
        this.basePrice = data.basePrice();
        this.status = RoomStatus.FREE;
    }

    public void updateData(UpdateRoomDTO data) {
        if (data.type()!=null) {
            this.type = data.type();
        }

        if (data.basePrice()!=null) {
            this.basePrice = data.basePrice();
        }
    }
}
