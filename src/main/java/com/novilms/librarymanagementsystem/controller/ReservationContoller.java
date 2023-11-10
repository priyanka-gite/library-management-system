package com.novilms.librarymanagementsystem.controller;

import com.novilms.librarymanagementsystem.dtos.ReservationDto;
import com.novilms.librarymanagementsystem.services.ReservationServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("reservations")
public class ReservationContoller {

    private final ReservationServices reservationServices;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservation(){
        List<ReservationDto> dtos = reservationServices.getAllReservations();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable("id") Long id){
        ReservationDto reservationDto = reservationServices.getReservationById(id);
        return ResponseEntity.ok(reservationDto);
    }

    @PostMapping
    public ResponseEntity<Object> addReservation (@RequestBody ReservationDto reservationDto){
        ReservationDto dto = reservationServices.addReservation(reservationDto);
        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.id()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable Long id) {
        reservationServices.deleteReservation(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDto newReservation) {
        ReservationDto dto = reservationServices.updateReservation(id, newReservation);
        return ResponseEntity.ok().body(dto);
    }


}
