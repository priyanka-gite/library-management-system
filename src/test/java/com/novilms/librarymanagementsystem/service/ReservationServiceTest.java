package com.novilms.librarymanagementsystem.service;

import com.novilms.librarymanagementsystem.dtos.BookDto;
import com.novilms.librarymanagementsystem.dtos.ReservationDto;
import com.novilms.librarymanagementsystem.dtos.SubscriptionDto;
import com.novilms.librarymanagementsystem.dtos.UserDto;
import com.novilms.librarymanagementsystem.exceptions.BusinessException;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.*;
import com.novilms.librarymanagementsystem.repository.BookRepository;
import com.novilms.librarymanagementsystem.repository.ReserveRepository;
import com.novilms.librarymanagementsystem.repository.SubscriptionRepository;
import com.novilms.librarymanagementsystem.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.LocalDate.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReserveRepository reservationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void addReservation_UserNotFound() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, null, "email");
        when(userRepository.findByEmail("email")).thenReturn(Optional.empty());
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("User with email email not found", recordNotFoundException.getMessage());
    }
    @Test
    void addReservation_SubscriptoinExpired() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, null, "email");
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().minusDays(8),SubscriptionType.ABOVE_EIGHTEEN,10,10,null))));
        BusinessException recordNotFoundException = assertThrows(BusinessException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("Subscription has expired.", recordNotFoundException.getMessage());
    }
    @Test
    void addReservation_UnknownIsbn() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton("unknown"), "email");
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().plusDays(1),SubscriptionType.ABOVE_EIGHTEEN,10,10,null))));
        when(bookRepository.findByIsbn("unknown")).thenReturn(Optional.empty());
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("Book with isbn unknown not found",recordNotFoundException.getMessage());
    }
    @Test
    void addReservation_MaxBooksReserved() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton("unknown"), "email");
//        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton(new BookDto(null,null, "unknown","category",0,null,null)), new UserDto(null, "username", "password", null, "email", null, null, null, new SubscriptionDto(100l,null, LocalDate.now().minusDays(1),10,5, SubscriptionType.ABOVE_EIGHTEEN,null)));
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().plusDays(1),SubscriptionType.ABOVE_EIGHTEEN,10,10,null))));
        when(bookRepository.findByIsbn("unknown")).thenReturn(Optional.of(new Book()));
        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("Reservation exceeds book limit of the subscription.", businessException.getMessage());
    }
    @Test
    void addReservation_KidsBookBelowEighteen() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton("unknown"), "email");
//        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton(new BookDto(null,null, "unknown","category",0,null,null)), new UserDto(null, "username", "password", null, "email", null, null, null, new SubscriptionDto(100l,null, LocalDate.now().minusDays(1),10,5, SubscriptionType.ABOVE_EIGHTEEN,null)));
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().plusDays(1),SubscriptionType.BELOW_EIGHTEEN,10,0,null))));
        when(bookRepository.findByIsbn("unknown")).thenReturn(Optional.of(new Book(100l, null,null,"category",10,   10,null,null)));
        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("Using kids subscription only Kids books can be subscribed.", businessException.getMessage());
    }

    @Test
    void addReservation_MaxBookBorrowed() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton("unknown"), "email");
//        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton(new BookDto(null,null, "unknown","category",0,null,null)), new UserDto(null, "username", "password", null, "email", null, null, null, new SubscriptionDto(100l,null, LocalDate.now().minusDays(1),10,5, SubscriptionType.ABOVE_EIGHTEEN,null)));
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().plusDays(1),SubscriptionType.ABOVE_EIGHTEEN,10,0,null))));
        when(bookRepository.findByIsbn("unknown")).thenReturn(Optional.of(new Book(100l, null,null,"category",10,   10,null,null)));
        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.addReservation(reservationDto));
        assertEquals("Max number of copies already borrowed", businessException.getMessage());
    }

    @Test
    void addReservation() {
        ReservationDto reservationDto = new ReservationDto(100l, null, null, false,  Collections.singleton("unknown"), "email");
//        ReservationDto reservationDto = new ReservationDto(100l, null, null, false, Collections.singleton(new BookDto(null,null, "unknown","category",0,null,null)), new UserDto(null, "username", "password", null, "email", null, null, null, new SubscriptionDto(100l,null, LocalDate.now().minusDays(1),10,5, SubscriptionType.ABOVE_EIGHTEEN,null)));
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(new User(null,null,null,null,null,null,null,null,new Subscription(null, now().minusDays(10), now().plusDays(1),SubscriptionType.BELOW_EIGHTEEN,10,0,null))));
        when(bookRepository.findByIsbn("unknown")).thenReturn(Optional.of(new Book(100l, null,null,"kids",10,   8,null,null)));
        reservationService.addReservation(reservationDto);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void deleteReservation() {
        reservationService.deleteReservation(100L);
        verify(reservationRepository).deleteById(100L);
    }

    @Test
    void updateReservation_recordNotFound() {
        when(reservationRepository.findById(100L)).thenReturn(Optional.empty());
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> reservationService.updateReservation(100L, null));
        assertEquals("Reservation not found", recordNotFoundException.getMessage());
    }

    @Test
    void updateReservation() {
        LocalDate today = now();
        LocalDate todayPlus30Days = today.plusDays(30);
        LocalDate todayMinus5Days = today.minusDays(5);
        LocalDate todayPlus25Days = today.plusDays(25);
        Set<String> isbns = Set.of("isbn1", "isbn2");
        ReservationDto reservationDto = new ReservationDto(100L, today, todayPlus30Days, false, isbns, null);
        Book book1 = new Book(101L,"isbn1",null,null,10, 5,null,null);
        when(bookRepository.findByIsbn("isbn1")).thenReturn(Optional.of(book1));
        Book book2 = new Book(101L,"isbn2",null,null,10, 5,null,null);
        when(bookRepository.findByIsbn("isbn2")).thenReturn(Optional.of(book2));

        Subscription subscription = new Subscription(100L,null, null, null,10,5,null);
        User user = new User(100L,null,null,null,null,null,null,null,subscription);
        Reservation reservation = new Reservation(100L, todayMinus5Days, todayPlus25Days, false, Set.of(book1), user);
        when(reservationRepository.findById(100L)).thenReturn(Optional.of(reservation));

        ReservationDto result = reservationService.updateReservation(100L, reservationDto);

        verify(reservationRepository).save(any(Reservation.class));
        assertEquals(today, reservation.getReservationDate());
        assertFalse(reservation.getIsReturned());
        assertEquals(todayPlus30Days, reservation.getReturnDate());
        Set<String> reservedBooks = reservation.getReservedBooks().stream().map(rb -> rb.getIsbn()).collect(Collectors.toSet());
        assert(isbns.size() == reservedBooks.size() && reservedBooks.containsAll(isbns) && isbns.containsAll(reservedBooks));
    }

    @Test
    void updateReservation_MaxBookReserved() {
        ReservationDto reservationDto = new ReservationDto(100L, now(), now().plusDays(30), false, Set.of("isbn1","isbn2"), null);
        Book book1 = new Book(101L,"isbn1",null,null,10, 5,null,null);
        when(bookRepository.findByIsbn("isbn1")).thenReturn(Optional.of(book1));
        Book book2 = new Book(101L,"isbn2",null,null,10, 5,null,null);
        when(bookRepository.findByIsbn("isbn2")).thenReturn(Optional.of(book2));

        Subscription subscription = new Subscription(100L,null, null, null,10,9,null);
        User user = new User(100L,null,null,null,null,null,null,null,subscription);
        Reservation reservation = new Reservation(100L, now().minusDays(5), now().plusDays(25), false, Set.of(book1), user);
        when(reservationRepository.findById(100L)).thenReturn(Optional.of(reservation));

        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.updateReservation(100L, reservationDto));

        assertEquals("Reservation exceeds book limit of the subscription.", businessException.getMessage());
    }
    
    @Test
    void updateReservation_KidsSubscription() {
        ReservationDto reservationDto = new ReservationDto(100L, now(), now().plusDays(30), false, Set.of("isbn1","isbn2"), null);
        Book book1 = new Book(101L,"isbn1",null,"category",10, 5,null,null);
        when(bookRepository.findByIsbn("isbn1")).thenReturn(Optional.of(book1));
        Book book2 = new Book(101L,"isbn2",null,"category2",10, 5,null,null);
        when(bookRepository.findByIsbn("isbn2")).thenReturn(Optional.of(book2));

        Subscription subscription = new Subscription(100L,null, null, SubscriptionType.BELOW_EIGHTEEN,10,0,null);
        User user = new User(100L,null,null,null,null,null,null,null,subscription);
        Reservation reservation = new Reservation(100L, now().minusDays(5), now().plusDays(25), false, Set.of(book1), user);
        when(reservationRepository.findById(100L)).thenReturn(Optional.of(reservation));

        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.updateReservation(100L, reservationDto));

        assertEquals("Using kids subscription only Kids books can be subscribed.", businessException.getMessage());
    }
    @Test
    void updateReservation_BookOverSubscribed() {
        ReservationDto reservationDto = new ReservationDto(100L, now(), now().plusDays(30), false, Set.of("isbn1","isbn2"), null);
        Book book1 = new Book(101L,"isbn1",null,"category",10, 5,null,null);
        when(bookRepository.findByIsbn("isbn1")).thenReturn(Optional.of(book1));
        Book book2 = new Book(101L,"isbn2",null,null,10, 10,null,null);
        when(bookRepository.findByIsbn("isbn2")).thenReturn(Optional.of(book2));

        Subscription subscription = new Subscription(100L,null, null, SubscriptionType.ABOVE_EIGHTEEN,10,0,null);
        User user = new User(100L,null,null,null,null,null,null,null,subscription);
        Reservation reservation = new Reservation(100L, now().minusDays(5), now().plusDays(25), false, Set.of(book1), user);
        when(reservationRepository.findById(100L)).thenReturn(Optional.of(reservation));

        BusinessException businessException = assertThrows(BusinessException.class, () -> reservationService.updateReservation(100L, reservationDto));

        assertEquals("Max number of copies already borrowed", businessException.getMessage());
    }

    @Test
    void returnBook_ReservationNotFound() {
        when(reservationRepository.findById(100L)).thenReturn(Optional.empty());
        RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class, () -> reservationService.returnBooks(100L));
        assertEquals("Reservation not found", recordNotFoundException.getMessage());
    }

    @Test
    void returnBook() {
        Book book1 = new Book(100L,"isbn1",null,null,10, 8,null,null);
        Book book2 = new Book(101L,"isbn2",null,null,10, 5,null,null);
        Subscription subscription = new Subscription(100L,null, null, null,10,9,null);
        User user = new User(100L,null,null,null,null,null,null,null,subscription);
        Reservation reservation = new Reservation(100L, null, null, false, Set.of(book1, book2), user);
        when(reservationRepository.findById(100L)).thenReturn(Optional.of(reservation));

        reservationService.returnBooks(100L);

        assertTrue(reservation.getIsReturned());
        verify(bookRepository, times(2)).save(any(Book.class));
        assertEquals(7, book1.getNumberOfCopiesBorrowed());
        assertEquals(4, book2.getNumberOfCopiesBorrowed());

        verify(subscriptionRepository).save(any(Subscription.class));
        assertEquals(7, subscription.getNumberOfBooksBorrowed());

        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void getAllReservations() {
        Book book1 = new Book(null, "isbn1", null,null,0,0,null,null);
        Book book2 = new Book(null, "isbn2", null,null,0,0,null,null);
        User user = new User(null,null,null,null, "email", null, null, null, null);
        LocalDate now = now();
        LocalDate nowPlus30 = now.plusDays(30);
        LocalDate nowPlus10 = now.plusDays(10);
        when(reservationRepository.findAll()).thenReturn(List.of(
                new Reservation(100L, now, nowPlus30,false, Set.of(book1, book2), user),
                new Reservation(101L, now, nowPlus10,true,Set.of(book1),user)));

        List<ReservationDto> result = reservationService.getAllReservations();

        assertEquals(2,result.size());
        for (ReservationDto reservationDto : result) {
            if(reservationDto.id().equals(100L)) {
                assertEquals(now, reservationDto.reservationDate());
                assertEquals(nowPlus30, reservationDto.returnDate());
                assertFalse(reservationDto.isReturned());
                Set<String> reservedIsbn = reservationDto.reservedIsbn();
                assertEquals(2, reservedIsbn.size());
                assertTrue(reservedIsbn.contains("isbn1"));
                assertTrue(reservedIsbn.contains("isbn2"));
                assertEquals("email", reservationDto.userEmail());
            } else if (reservationDto.id().equals(101L)){
                assertEquals(now, reservationDto.reservationDate());
                assertEquals(nowPlus10, reservationDto.returnDate());
                assertTrue(reservationDto.isReturned());
                Set<String> reservedIsbn = reservationDto.reservedIsbn();
                assertEquals(1, reservedIsbn.size());
                assertTrue(reservedIsbn.contains("isbn1"));
                assertEquals("email", reservationDto.userEmail());
            } else {
                fail("Unexpected Id");
            }
        }
    }

    @Test
    void getReservationById() {
        Book book1 = new Book(null, "isbn1", null,null,0,0,null,null);
        User user = new User(null,null,null,null, "email", null, null, null, null);
        LocalDate now = now();
        LocalDate nowPlus10 = now.plusDays(10);
        when(reservationRepository.findById(101L)).thenReturn(Optional.of(new Reservation(101L, now, nowPlus10,true,Set.of(book1),user)));

        ReservationDto reservationDto = reservationService.getReservationById(101L) ;

        assertEquals(now, reservationDto.reservationDate());
        assertEquals(nowPlus10, reservationDto.returnDate());
        assertTrue(reservationDto.isReturned());
        Set<String> reservedIsbn = reservationDto.reservedIsbn();
        assertEquals(1, reservedIsbn.size());
        assertTrue(reservedIsbn.contains("isbn1"));
        assertEquals("email", reservationDto.userEmail());

    }
}