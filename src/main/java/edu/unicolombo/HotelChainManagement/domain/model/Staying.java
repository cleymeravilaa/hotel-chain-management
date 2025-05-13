package edu.unicolombo.HotelChainManagement.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stayings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( of = "stayingId")
public class Staying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stayingId;

    @OneToMany(mappedBy = "staying", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StayingRoom> stayingRoom = new ArrayList<>();
}
