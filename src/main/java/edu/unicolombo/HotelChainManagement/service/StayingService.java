package edu.unicolombo.HotelChainManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.unicolombo.HotelChainManagement.infrastructure.errors.exception.BusinessLogicValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.unicolombo.HotelChainManagement.domain.model.Invoice;
import edu.unicolombo.HotelChainManagement.domain.model.Room;
import edu.unicolombo.HotelChainManagement.domain.model.RoomStatus;
import edu.unicolombo.HotelChainManagement.domain.model.Staying;
import edu.unicolombo.HotelChainManagement.domain.model.StayingRoom;
import edu.unicolombo.HotelChainManagement.domain.model.StayingRoomId;
import edu.unicolombo.HotelChainManagement.domain.repository.BookingRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.StayingRepository;
import edu.unicolombo.HotelChainManagement.domain.repository.StayingRoomRepository;
import edu.unicolombo.HotelChainManagement.dto.staying.StayingDTO;
import edu.unicolombo.HotelChainManagement.dto.staying.UpdateStayingDTO;
import edu.unicolombo.HotelChainManagement.dto.staying.CheckOutRoomDTO;
import jakarta.transaction.Transactional;

@Service
public class StayingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    public StayingRepository stayingRepository;

    @Autowired
    public StayingRoomRepository stayingRoomRepository;

    @Autowired
    public InvoiceService invoiceService;

    @Transactional
    public StayingDTO toCheckIn(Long bookingId) {
        var booking = bookingRepository.getReferenceById(bookingId);

        Staying staying = new Staying();
        staying.setBooking(booking);
        staying.setStartDate(booking.getStartDate());
        staying.setEndDate(booking.getEndDate());
        
        Staying savedStaying = stayingRepository.save(staying);

        List<StayingRoom> stayingRooms = new ArrayList<>();
        for(Room room: booking.getRooms()){
            room.setStatus(RoomStatus.OCCUPIED);

            StayingRoomId id = new StayingRoomId(savedStaying.getStayingId(), room.getRoomId());

            StayingRoom stayingRoom = new StayingRoom();
            stayingRoom.setId(id);
            stayingRoom.setCheckInDate(staying.getStartDate());
            stayingRoom.setRoom(room);
            stayingRoom.setStaying(staying);

            stayingRooms.add(stayingRoom);
        }
        savedStaying.setStayingRoom(stayingRooms);
        
        stayingRoomRepository.saveAll(stayingRooms);
        savedStaying = stayingRepository.save(savedStaying);

        return new StayingDTO(savedStaying);

    }

    public List<StayingDTO> getAllStayings(){
        return stayingRepository.findAll().stream().map(StayingDTO::new).toList();
    }

    public StayingDTO getStayingById(Long stayingId) {
        var staying = stayingRepository.getReferenceById(stayingId);

        return new StayingDTO(staying);
    }

    public StayingDTO toCheckOutRooms(Long stayingId, UpdateStayingDTO data) {
        Staying staying = stayingRepository.getReferenceById(stayingId);

        if (stayingComplete(staying)){
            throw new BusinessLogicValidationException("La estadia esta completa no puede actualizar la estadia");
        }

        for(CheckOutRoomDTO checkOutRoom: data.checkOutRoomDTOs()){
            if (!stayingRoomRepository.existsByStayingIdAndRoomId(stayingId, checkOutRoom.roomId())){
                throw new BusinessLogicValidationException("La habitación "+ checkOutRoom.roomId()+ " no pertenece a la estancia "+ stayingId);
            }
        }
        List<StayingRoom> stayingRooms = staying.getStayingRoom();

        for(StayingRoom stayingRoom: staying.getStayingRoom()){
            for(CheckOutRoomDTO checkOutRoom: data.checkOutRoomDTOs()){
                if(stayingRoom.getRoom().getRoomId().equals(checkOutRoom.roomId())){
                    stayingRoom.setCheckOutDate(LocalDate.now());
                    System.out.println("fecha checkout ahora: "+ LocalDate.now());
                    stayingRoom.setNotes(checkOutRoom.notes());
                    stayingRoom.getRoom().setStatus(RoomStatus.FREE);
                }
            }
        }

        stayingRoomRepository.saveAll(stayingRooms);

        staying.setStayingRoom(stayingRooms);

        if(checkOutRoomsComplete(stayingRooms)){
            Invoice invoice = invoiceService.generateInvoice(stayingId);
            staying.setInvoice(invoice);
        }

        return new StayingDTO(stayingRepository.save(staying));
    }

    public StayingDTO updateStaying(Long stayingId, UpdateStayingDTO data) {
        var staying = stayingRepository.getReferenceById(stayingId);
        return new StayingDTO(stayingRepository.save(staying));

    }

    public boolean checkOutRoomsComplete(List<StayingRoom> stayingRooms){
        for(StayingRoom stayingRoom: stayingRooms){
            if(stayingRoom.getCheckOutDate()==null){
                return false;
            }
        }
        return true;
    }

    public boolean stayingComplete(Staying staying){
        return staying.getInvoice()!=null;
    }
    public void deleteStaying(Long stayingId){

    }

}
