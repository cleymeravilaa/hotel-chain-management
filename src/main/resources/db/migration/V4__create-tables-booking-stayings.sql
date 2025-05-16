-- Tabla bookings (reservas)
CREATE TABLE bookings (
    booking_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    advance_deposit DECIMAL(10, 2) DEFAULT 0.00,
    customer_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id)
);

-- Tabla intermedia booking_room (reserva-habitación)
CREATE TABLE booking_room (
    booking_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    PRIMARY KEY (booking_id, room_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- Tabla stayings (estancias)
CREATE TABLE stayings (
    staying_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    booking_id BIGINT UNIQUE NOT NULL, -- Relación 1:1 con booking
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- Tabla staying_room (estancia-habitación con fechas específicas)
CREATE TABLE staying_room (
    staying_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NULL,
    notes TEXT,
    PRIMARY KEY (staying_id, room_id),
    FOREIGN KEY (staying_id) REFERENCES stayings(staying_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);