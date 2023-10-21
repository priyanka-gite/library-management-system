package com.novilms.librarymanagementsystem.repository;

import com.novilms.librarymanagementsystem.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    List<Member> findAllMembersByName(String name);
}
