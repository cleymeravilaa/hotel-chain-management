package edu.unicolombo.HotelChainManagement.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import edu.unicolombo.HotelChainManagement.dto.booking.BookingDTO;
import edu.unicolombo.HotelChainManagement.dto.booking.RegisterBookingDTO;
import edu.unicolombo.HotelChainManagement.dto.booking.UpdateBookingDTO;
import edu.unicolombo.HotelChainManagement.service.BookingService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    public BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> registerBooking(@RequestBody RegisterBookingDTO data, UriComponentsBuilder uriBuilder){
        var registeredBooking = bookingService.registerBooking(data);
        URI url = uriBuilder.path("/bookings/{bookingId}").buildAndExpand(registeredBooking.bookingId()).toUri();
        return ResponseEntity.created(url).body(registeredBooking);
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long bookingId){
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @DeleteMapping("/{bookingId}")
    @Transactional
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId){
        bookingService.deleleById(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookingId}")
    @Transactional
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingDTO data){
        return ResponseEntity.ok(bookingService.updateBooking(bookingId, data));
    }

}
