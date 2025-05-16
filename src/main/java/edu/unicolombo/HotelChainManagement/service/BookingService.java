package edu.unicolombo.HotelChainManagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.HotelChainManagement.domain.model.Booking;
import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.model.RoomStatus;
import edu.unicolombo.HotelChainManagement.domain.repository.BookingRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.CustomerRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.HotelRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.RoomRepository;
import edu.unicolombo.HotelChainManagement.dto.booking.BookingDTO;
import edu.unicolombo.HotelChainManagement.dto.booking.RegisterBookingDTO;
import edu.unicolombo.HotelChainManagement.dto.booking.UpdateBookingDTO;
import jakarta.transaction.Transactional;

@Service
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired 
    public CustomerRepository customerRepository;

    @Autowired
    public HotelRepository hotelRepository;

    @Transactional
    public BookingDTO registerBooking(RegisterBookingDTO data){
        var customer = customerRepository.getReferenceById(data.customerId());
        var hotel = hotelRepository.getReferenceById(data.hotelId());
        var rooms = roomRepository.findByHotel_hotelIdAndRoomIdIn(hotel.getHotelId(), data.roomIds());

        var advanceDeposit = reserveRoomsAndCalculeAdvanceDeposit(data.startDate(), data.endDate(), rooms);
        var booking = new Booking(customer, hotel, rooms, data.startDate(), data.endDate(), advanceDeposit);
        roomRepository.saveAll(rooms);
        return new BookingDTO(bookingRepository.save(booking));
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream().map(BookingDTO::new).toList();
    }

    public BookingDTO getBookingById(Long bookingId) {
        return new BookingDTO(bookingRepository.getReferenceById(bookingId));
    }

    public void deleleById(Long bookingId) {

        Booking booking = bookingRepository.getReferenceById(bookingId);
        List<Room> rooms = booking.getRooms();
        for(Room room: rooms){
            room.setStatus(RoomStatus.FREE);
        }
        roomRepository.saveAll(rooms);
       bookingRepository.deleteById(bookingId); 
    }

    public BookingDTO updateBooking(Long bookingId, UpdateBookingDTO data) {
        var booking = bookingRepository.getReferenceById(bookingId);

        if (data.hotelId()!=null) {
            var hotel = hotelRepository.getReferenceById(data.hotelId());
            booking.setHotel(hotel);
        }
        if (data.startDate()!=null) {
            booking.setStartDate(data.startDate()); 
        }
        if (data.endDate()!=null) {
            booking.setEndDate(data.endDate());
        }

        if (data.roomIds() !=null && data.hotelId()==null) {
            List<Room> rooms = roomRepository.findByHotel_hotelIdAndRoomIdIn(booking.getHotel().getHotelId(), data.roomIds());
            var advanceDeposit = reserveRoomsAndCalculeAdvanceDeposit(booking.getStartDate(), booking.getEndDate(), rooms);
            booking.setRooms(rooms);
            booking.setAdvanceDeposit(advanceDeposit);
        } else if (data.roomIds()!=null && data.hotelId()!=null){
            List<Room> rooms = roomRepository.findByHotel_hotelIdAndRoomIdIn(data.hotelId(), data.roomIds());
            var advanceDeposit = reserveRoomsAndCalculeAdvanceDeposit(booking.getStartDate(), booking.getEndDate(), rooms);
            booking.setRooms(rooms);
            booking.setAdvanceDeposit(advanceDeposit);
        }
        return new BookingDTO(bookingRepository.save(booking));
    }

    public Double reserveRoomsAndCalculeAdvanceDeposit(LocalDate startDate, LocalDate endDate, List<Room> rooms){
        // calcular el deposito por adelantado de las reservaciones de las habitaciones
        Double total = 0D;
        Long bookingDays = ChronoUnit.DAYS.between(startDate, endDate);

        for(Room room: rooms){
            room.setStatus(RoomStatus.BOOKED);
            total += bookingDays * room.getBasePrice();
        }
        return total * .2;
    }
}
