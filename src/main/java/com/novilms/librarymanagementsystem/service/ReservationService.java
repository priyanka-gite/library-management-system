package com.novilms.librarymanagementsystem.service;

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
public class ReservationService {

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
        updateReservation.setReservationDate(reservationDto.reservationDate());
        updateReservation.setReturnDate(reservationDto.returnDate());
        updateReservation.setIsReturned(reservationDto.isReturned());
        updateReservation.setReservedBooks(reservationDto.BooksReserved());
        updateReservation.setUser(reservationDto.user());
        return reservationDto;
    }


//    ---------CONVERSIONS----------------------------

    private Reservation convertDtoToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDto.reservationDate());
        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setIsReturned(reservationDto.isReturned());
        reservation.setReservedBooks(reservationDto.BooksReserved());
        reservation.setUser(reservationDto.user());
        return reservation;
    }

    private ReservationDto convertReservationToDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto(reservation.getId(),reservation.getReservationDate(),reservation.getReturnDate(),reservation.getIsReturned(),reservation.getReservedBooks(),reservation.getUser());
        return reservationDto;
    }

}


