package com.novilms.librarymanagementsystem.controller;


import com.novilms.librarymanagementsystem.dtos.MemberDto;
import com.novilms.librarymanagementsystem.services.MemberServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("members")
public class MemberContoller {

    private final MemberServices memberServices;

    public MemberContoller(MemberServices memberServices) {
        this.memberServices = memberServices;
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getAllMembers(@RequestParam(value = "name", required = false) Optional<String> name) {
        List<MemberDto> dtos;
        if (name.isEmpty()) {
            dtos = memberServices.getAllMembers();
        } else {
            dtos = memberServices.getMembersByName(name.get());
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getMemberById(@PathVariable("id") Long id) {
        MemberDto memberDto = memberServices.getMemberById(id);
        return ResponseEntity.ok(memberDto);
    }

    @PostMapping
    public ResponseEntity<Object> addMember(@RequestBody MemberDto memberDto) {

        MemberDto dto = memberServices.addMember(memberDto);

        URI uri = URI.create(ServletUriComponentsBuilder.
                fromCurrentRequest().
                path(new StringBuilder().append("/").append(dto.getId()).toString()).toUriString());
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Object> deleteMember(@PathVariable Long id) {
        memberServices.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMember(@PathVariable Long id, @RequestBody MemberDto newMember) {
        MemberDto dto = memberServices.updateMember(id, newMember);

        return ResponseEntity.ok().body(dto);
    }


}
