package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.TeamMember;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.interfaces.PhotoData;
import com.remodelingllc.api.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamMemberService {

    private TeamMemberRepository teamMemberRepository;

    public TeamMemberService(final TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public List<TeamMember> findAll() {
        return (List<TeamMember>) teamMemberRepository.findAll();
    }

    public TeamMember findById(final int id) {
        var oldMember = teamMemberRepository.findById(id);
        if (oldMember.isEmpty()) {
            throw new EntityNotFoundException("Team Member Not Found");
        }
        return oldMember.get();
    }

    public PhotoData findPhotoById(final int id) {
        var oldMember = teamMemberRepository.findById(id);
        if (oldMember.isEmpty()) {
            throw new EntityNotFoundException("Team Member Not Found");
        }
        return teamMemberRepository.findPhotoById(id);
    }

    public TeamMember save(final TeamMember member) {
        return teamMemberRepository.save(member);
    }

    public TeamMember update(final TeamMember member) {
        var oldMember = teamMemberRepository.findById(member.getId());
        if (oldMember.isEmpty()) {
            throw new EntityNotFoundException("Team Member Not Found");
        }
        return teamMemberRepository.save(member);
    }

    public void delete(final int id) {
        var oldMember = teamMemberRepository.findById(id);
        if (oldMember.isEmpty()) {
            throw new EntityNotFoundException("Team Member Not Found");
        }
        teamMemberRepository.delete(oldMember.get());
    }
}
