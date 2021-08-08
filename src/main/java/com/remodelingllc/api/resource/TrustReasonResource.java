package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.TrustReasonModelDTO;
import com.remodelingllc.api.entity.TrustReason;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.service.TrustReasonService;
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
public class TrustReasonResource {

    private final TrustReasonService trustReasonService;

    public TrustReasonResource(final TrustReasonService trustReasonService) {
        this.trustReasonService = trustReasonService;
    }

    @GetMapping(value = "/trustReason", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TrustReason> findAll() {
        return trustReasonService.findAll();
    }

    @GetMapping(value = "/trustReason/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TrustReason findById(@PathVariable final int id) {
        return trustReasonService.findById(id);
    }

    @GetMapping(value = "/trustReason/image/{id}")
    public ResponseEntity<byte[]> findImageById(@PathVariable final int id) {
        var imageData = trustReasonService.findImageById(id);
        var mediaType = ContentTypeHelper.getMediaType(imageData.getImageExtension());
        return ResponseEntityHelper.responseForFile(imageData.getImage(), mediaType);
    }

    @PostMapping(value = "/trustReason", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public TrustReason save(@Validated @ModelAttribute final TrustReasonModelDTO model) {
        return trustReasonService.save(this.convertModelToTrustReason(model));
    }

    @PutMapping(value = "/trustReason", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public TrustReason update(@Validated @ModelAttribute  final TrustReasonModelDTO model) {
        return trustReasonService.update(this.convertModelToTrustReason(model));
    }

    @DeleteMapping(value = "/trustReason/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public void delete(@PathVariable final int id) {
        trustReasonService.delete(id);
    }

    private TrustReason convertModelToTrustReason(final TrustReasonModelDTO model) {
        try {
            var reason = new TrustReason();
            reason.setId(model.getId());
            reason.setTitle(model.getTitle());
            reason.setDescription(model.getDescription());
            reason.setImage(model.getImage().getBytes());
            // Validate content type
            String contentType = model.getImage().getContentType();
            var mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
            log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
            reason.setImageExtension(contentType);
            return reason;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }

}
