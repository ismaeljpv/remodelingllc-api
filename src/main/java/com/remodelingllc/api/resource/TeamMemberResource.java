package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.TeamMemberModelDTO;
import com.remodelingllc.api.entity.TeamMember;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.PhotoData;
import com.remodelingllc.api.service.TeamMemberService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Log4j2
public class TeamMemberResource {

    private TeamMemberService teamMemberService;

    public TeamMemberResource(final TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TeamMember> findAll() {
        return teamMemberService.findAll();
    }

    @GetMapping(value = "/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamMember findById(@PathVariable final int id) {
        return teamMemberService.findById(id);
    }

    @GetMapping(value = "/team/photo/{id}")
    public ResponseEntity<byte[]> findPhotoById(@PathVariable final int id) {
        PhotoData photoData = teamMemberService.findPhotoById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(photoData.getPhotoExtension());
        return ResponseEntityHelper.responseForFile(photoData.getPhoto(), mediaType);
    }

    @PostMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public TeamMember save(@Validated @ModelAttribute  final TeamMemberModelDTO model) {
        return teamMemberService.save(this.convertModelToTeamMember(model));
    }

    @PutMapping(value = "/team", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public TeamMember update(@Validated @ModelAttribute  final TeamMemberModelDTO model) {
        return teamMemberService.update(this.convertModelToTeamMember(model));
    }

    @DeleteMapping(value = "/team/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final int id) {
        teamMemberService.delete(id);
    }

    private TeamMember convertModelToTeamMember(final TeamMemberModelDTO model) {
        try {
            var member = new TeamMember();
            member.setId(model.getId());
            member.setName(model.getName());
            member.setPosition(model.getPosition());
            member.setPhoto(model.getPhoto().getBytes());
            // Validate content type
            String contentType = model.getPhoto().getContentType();
            MediaType mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
            log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
            member.setPhotoExtension(contentType);
            return member;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }
}
