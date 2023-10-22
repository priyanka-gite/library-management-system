package com.novilms.librarymanagementsystem.services;

import com.novilms.librarymanagementsystem.dtos.MemberDto;
import com.novilms.librarymanagementsystem.exceptions.RecordNotFoundException;
import com.novilms.librarymanagementsystem.model.Member;
import com.novilms.librarymanagementsystem.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class MemberServices {
    private final MemberRepository memberRepository;

    public List<MemberDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member member : members) {
            memberDtoList.add(convertMemberToDto(member));
        }
        return memberDtoList;
    }

    public List<MemberDto> getMembersByName(String name) {
        List<Member> memberList = memberRepository.findAllMembersByName(name);
        return convertMemberListToDtoList(memberList);
    }

    public MemberDto getMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            MemberDto memberDto = convertMemberToDto(member.get());
            return memberDto;
        } else {
            throw new RecordNotFoundException("Member not Found");
        }
    }

    public MemberDto addMember(MemberDto memberDto) {
        memberRepository.save(convertDtoToMember(memberDto));
        return memberDto;
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDto updateMember(Long id, MemberDto memberDto) {
        if (!memberRepository.existsById(id)) {
            throw new RecordNotFoundException("Book Not Found");
        }
        Member updateMember = memberRepository.findById(id).orElse(null);
        updateMember.setName(memberDto.getName());
        updateMember.setAddress(memberDto.getName());
        updateMember.setEmail(memberDto.getEmail());
        updateMember.setMobileNumber(memberDto.getMobileNumber());
        updateMember.setSubscription(memberDto.getSubscription());
        memberRepository.save(updateMember);
        return memberDto;
    }


    //    ---------------CONVERSIONS--------------------
    public MemberDto convertMemberToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setAddress(member.getAddress());
        memberDto.setName(memberDto.getName());
        memberDto.setEmail(memberDto.getEmail());
        memberDto.setMobileNumber(member.getMobileNumber());
        memberDto.setSubscription(member.getSubscription());
        return memberDto;
    }

    public Member convertDtoToMember(MemberDto memberDto) {
        Member member = new Member();
        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());
        member.setEmail(memberDto.getEmail());
        member.setMobileNumber(memberDto.getMobileNumber());
        member.setSubscription(memberDto.getSubscription());
        return member;
    }

    private List<MemberDto> convertMemberListToDtoList(List<Member> memberList) {
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member member : memberList) {
            MemberDto dto = convertMemberToDto(member);
            memberDtoList.add(dto);
        }
        return memberDtoList;
    }

}
