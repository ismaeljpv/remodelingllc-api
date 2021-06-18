package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.ServiceModelDTO;
import com.remodelingllc.api.entity.Services;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.service.ServicesService;
import com.remodelingllc.api.util.ContentTypeHelper;
import com.remodelingllc.api.util.ResponseEntityHelper;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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
    public Services save(@Validated @ModelAttribute final ServiceModelDTO service) {
        return servicesService.save(this.convertModelToService(service));
    }

    @PutMapping(value = "/services", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Services update(@Validated @ModelAttribute final ServiceModelDTO service) {
        return servicesService.update(this.convertModelToService(service));
    }

    @DeleteMapping(value = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
            services.setThumbnailExtension(model.getThumbnail().getContentType());
            return services;
        } catch (IOException e) {
            throw new BadRequestException("Invalid File Format");
        }
    }

}
