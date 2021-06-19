package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.ServiceModelDTO;
import com.remodelingllc.api.entity.Services;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.service.ServicesService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
public class ServicesResource {

    private final ServicesService servicesService;

    public ServicesResource(final ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @GetMapping(value = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Services> findAll() {
        return servicesService.findAll();
    }

    @GetMapping(value = "/services", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Services> findAllActive(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        return servicesService.findAllActive(page, size);
    }

    @GetMapping(value = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Services findById(@PathVariable final int id) {
        return servicesService.findById(id);
    }

    @GetMapping(value = "/services/thumbnail/{id}")
    public ResponseEntity<byte[]> findThumbnailById(@PathVariable final int id) {
        ThumbnailData thumbnail = servicesService.findThumbnailById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(thumbnail.getThumbnailExtension());
        return ResponseEntityHelper.responseForFile(thumbnail.getThumbnail(), mediaType);
    }

    @PostMapping(value = "/services", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Services save(@Validated @ModelAttribute final ServiceModelDTO service) {
        return servicesService.save(this.convertModelToService(service));
    }

    @PutMapping(value = "/services", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Services update(@Validated @ModelAttribute final ServiceModelDTO service) {
        return servicesService.update(this.convertModelToService(service));
    }

    @DeleteMapping(value = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void delete(@PathVariable final int id) {
        servicesService.delete(id);
    }

    private Services convertModelToService(final ServiceModelDTO model) {
        try {
            Services services = new Services();
            services.setId(model.getId());
            services.setService(model.getService());
            services.setDescription(model.getDescription());
            services.setStatus(model.getStatus());
            services.setThumbnail(model.getThumbnail().getBytes());
            // Validate content type
            String contentType = model.getThumbnail().getContentType();
            MediaType mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
            log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
            services.setThumbnailExtension(contentType);
            return services;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }

}
