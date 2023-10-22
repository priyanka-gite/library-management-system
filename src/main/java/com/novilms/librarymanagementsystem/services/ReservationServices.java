package com.novilms.librarymanagementsystem.services;

import com.novilms.librarymanagementsystem.dtos.ReservationDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.repository.ReserveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ReservationServices {

    private final ReserveRepository reserveRepository;

    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reserveRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtoList.add(convertReservationToDto(reservation));
        }
        return reservationDtoList;
    }

    public ReservationDto getReservationById(Long id) {
        Optional<Reservation> reservation = reserveRepository.findById(id);
        ReservationDto reservationDto;
        if (reservation.isPresent()) {
            reservationDto = convertReservationToDto(reservation.get());
            return reservationDto;
        } else {
            throw new RecordNotFoundException("Reservation not Found");
        }
    }

    public ReservationDto addReservation(ReservationDto reservationDto) {
        reserveRepository.save(convertDtoToReservation(reservationDto));
        return reservationDto;
    }

    public void deleteReservation(Long id) {
        reserveRepository.deleteById(id);
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        if (!reserveRepository.existsById(id)) {
            throw new RecordNotFoundException("Reservation Not Found");
        }
        Reservation updateReservation = reserveRepository.findById(id).orElse(null);
        updateReservation.setReserveDate(reservationDto.getReserveDate());
        updateReservation.setReturnDate(reservationDto.getReturnDate());
        updateReservation.setIsReturned(reservationDto.getIsReturned());
        updateReservation.setListOfBooksReserved(reservationDto.getListOfBooksReserved());
        updateReservation.setSubscription(reservationDto.getSubscription());
        return reservationDto;

    }


//    ---------CONVERSIONS----------------------------

    private Reservation convertDtoToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setReserveDate(reservationDto.getReserveDate());
        reservation.setReturnDate(reservationDto.getReturnDate());
        reservation.setIsReturned(reservationDto.getIsReturned());
        reservation.setListOfBooksReserved(reservationDto.getListOfBooksReserved());
        reservation.setSubscription(reservationDto.getSubscription());
        return reservation;
    }

    private ReservationDto convertReservationToDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setReserveDate(reservation.getReserveDate());
        reservationDto.setReturnDate(reservation.getReturnDate());
        reservationDto.setIsReturned(reservation.getIsReturned());
        reservationDto.setListOfBooksReserved(reservation.getListOfBooksReserved());
        reservationDto.setSubscription(reservation.getSubscription());
        return reservationDto;
    }

}


