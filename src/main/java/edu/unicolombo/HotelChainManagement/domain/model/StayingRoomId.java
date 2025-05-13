package edu.unicolombo.HotelChainManagement.domain.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class StayingRoomId implements Serializable {
    private Long estanciaId;
    private Long habitacionId;
}