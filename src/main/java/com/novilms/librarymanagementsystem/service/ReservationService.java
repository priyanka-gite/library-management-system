package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.ReservationDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Book;
import com.novilms.librarymanagementsystem.model.Reservation;
import com.novilms.librarymanagementsystem.model.SubscriptionType;
import com.novilms.librarymanagementsystem.model.User;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import com.novilms.librarymanagementsystem.repository.ReserveRepository;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReservationService {

    private final ReserveRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtoList.add(convertReservationToDto(reservation));
        }
        return reservationDtoList;
    }

    public ReservationDto getReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        ReservationDto reservationDto;
        if (reservation.isPresent()) {
            reservationDto = convertReservationToDto(reservation.get());
            return reservationDto;
        } else {
            throw new RecordNotFoundException("Reservation not Found");
        }
    }

    public ReservationDto addReservation(ReservationDto reservationDto) {
        //validation
        // A. SUBSCRIPTION
        //      1.) User subscription validity
        //      2.) Subscription is for kids, then kids book or not
        //      3.) Max book limit for the subscription reached?
        // B. Book
        //      1.) Copy available for reservation

        // REservation
        // 1.) Find User using email
        // 2.) Find book
        // 3.) Create Reservation
        User user = userRepository.findByEmail(reservationDto.user().email()).orElseThrow(() -> new RecordNotFoundException("User with email " + reservationDto.user().email() + " not found"));
        if(user.getSubscription().getEndDate().isBefore(LocalDate.now())){
            throw new BusinessException("Subscription has expired.");
        }
        Set<Book> booksToReserve = reservationDto.booksReserved().stream().map(b -> bookRepository.findByIsbn(b.isbn()).orElseThrow(() -> new RecordNotFoundException("Book with isbn " + b.isbn() + " not found"))).collect(Collectors.toSet());
        if(user.getSubscription().getMaxBookLimit() < user.getSubscription().getNumberOfBooksBorrowed() + booksToReserve.size()) {
            throw new BusinessException("Reservation exceeds book limit of the subscription.");
        }
        if(user.getSubscription().getSubscriptionType() == SubscriptionType.BELOW_EIGHTEEN) {
             for(Book book : booksToReserve){
                if(!book.getCategory().equals("kids")){
                    throw new BusinessException("Using kids subscription only Kids books can be subscribed.");
                }
             }
        }
        for(Book book: booksToReserve) {
            if(book.getNumberOfCopies() < book.getNumberOfCopiesBorrowed() + 1) {
                throw new BusinessException("Max number of copies already borrowed");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setReservedBooks(booksToReserve);
        reservation.setUser(user);
        reservation.setReservationDate(reservationDto.reservationDate());
        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setIsReturned(Boolean.FALSE);
        reservationRepository.save(reservation);
        return reservationDto;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->new RecordNotFoundException("Reservation Not Found"));

        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setReservationDate(reservationDto.reservationDate());
        reservation.setIsReturned(reservationDto.isReturned());

        Set<Book> booksToReserve = reservationDto.booksReserved().stream().map(b -> bookRepository.findByIsbn(b.isbn()).orElseThrow(() -> new RecordNotFoundException("Book with isbn " + b.isbn() + " not found"))).collect(Collectors.toSet());
        if(reservation.getUser().getSubscription().getMaxBookLimit() < reservation.getUser().getSubscription().getNumberOfBooksBorrowed() + booksToReserve.size()) {
            throw new BusinessException("Reservation exceeds book limit of the subscription.");
        }
        if(reservation.getUser().getSubscription().getSubscriptionType() == SubscriptionType.BELOW_EIGHTEEN) {
            for(Book book : booksToReserve){
                if(!book.getCategory().equals("kids")){
                    throw new BusinessException("Using kids subscription only Kids books can be subscribed.");
                }
            }
        }
        for(Book book: booksToReserve) {
            if(book.getNumberOfCopies() < book.getNumberOfCopiesBorrowed() + 1) {
                throw new BusinessException("Max number of copies already borrowed");
            }
        }
        reservation.setReservedBooks(booksToReserve);
        reservationRepository.save(reservation);
        return reservationDto;
    }


//    ---------CONVERSIONS----------------------------

    private Reservation convertDtoToReservation(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(reservationDto.reservationDate());
        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setIsReturned(reservationDto.isReturned());
//        reservation.setReservedBooks(reservationDto.booksReserved());
//        reservation.setUser(reservationDto.user());
        return reservation;
    }

    private ReservationDto convertReservationToDto(Reservation reservation) {
//        ReservationDto reservationDto = new ReservationDto(reservation.getId(),reservation.getReservationDate(),reservation.getReturnDate(),reservation.getIsReturned(),reservation.getReservedBooks(),reservation.getUser());
//        return reservationDto;
        return null;
    }

    public void returnBook(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->new RecordNotFoundException("Reservation Not Found"));
        reservation.setIsReturned(Boolean.TRUE);
        reservationRepository.save(reservation);
    }
}


