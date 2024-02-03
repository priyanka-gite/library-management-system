package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.ReservationDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.*;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import com.novilms.librarymanagementsystem.repository.ReserveRepository;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
        return convertReservationToDto(getReservation(id));
    }

    public Reservation getReservation(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        ReservationDto reservationDto;
        if (reservation.isPresent()) {
            return reservation.get();
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
        Optional<User> userOpt = userRepository.findByEmail(reservationDto.userEmail());
        if(!userOpt.isPresent()) {
            throw new RecordNotFoundException("User with email " + reservationDto.userEmail() + " not found");
        }
        User user = userOpt.get();
        if(user.getSubscription().getEndDate().isBefore(LocalDate.now())){
            throw new BusinessException("Subscription has expired.");
        }
        Set<Book> booksToReserve = new HashSet<>();
        for (String isbn : reservationDto.reservedIsbn()) {
            Optional<Book> book = bookRepository.findByIsbn(isbn);
            if( book.isPresent()) {
                booksToReserve.add(book.get());
            } else {
                throw new RecordNotFoundException("Book with isbn " + isbn + " not found");
            }
        }
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
        Optional<Reservation> reservationOpt = reservationRepository.findById(id);
        if(!reservationOpt.isPresent()) {
            throw new RecordNotFoundException("Reservation not found");
        }
        Reservation reservation = reservationOpt.get();
        reservation.setReturnDate(reservationDto.returnDate());
        reservation.setReservationDate(reservationDto.reservationDate());

        Set<Book> booksToReserve = new HashSet<>();
        for (String b : reservationDto.reservedIsbn()) {
            Optional<Book> book = bookRepository.findByIsbn(b);
            if(!book.isPresent()) {
                throw new RecordNotFoundException("Book with isbn " + b + " not found");
            }
            booksToReserve.add(book.get());
        }
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

    private ReservationDto convertReservationToDto(Reservation reservation) {
        return new ReservationDto(reservation.getId(),reservation.getReservationDate(),reservation.getReturnDate(),reservation.getIsReturned(),reservation.getReservedBooks().stream().map(r -> r.getIsbn()).collect(Collectors.toSet()),reservation.getUser().getEmail());
    }

    public void returnBooks(Long reservationId) {
        // mark returned
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if(!reservationOpt.isPresent()) {
            throw new RecordNotFoundException("Reservation not found");
        }
        Reservation reservation = reservationOpt.get();
        reservation.setIsReturned(Boolean.TRUE);
        // update book count on the book object
        int bookCount =0;
        for (Book b : reservation.getReservedBooks()) {
            b.setNumberOfCopiesBorrowed(b.getNumberOfCopiesBorrowed() - 1);
            bookRepository.save(b);
            bookCount++;
        }
        // update book count on the subscription object
        Subscription subscription = reservation.getUser().getSubscription();
        subscription.setNumberOfBooksBorrowed(subscription.getNumberOfBooksBorrowed() - bookCount);
        subscriptionRepository.save(subscription);

        reservationRepository.save(reservation);
    }
}


