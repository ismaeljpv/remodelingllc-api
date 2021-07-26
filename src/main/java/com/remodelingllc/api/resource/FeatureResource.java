package com.remodelingllc.api.resource;

import com.remodelingllc.api.dto.FeatureModelDTO;
import com.remodelingllc.api.entity.Feature;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ImageData;
import com.remodelingllc.api.service.FeatureService;
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

@RestController
@Log4j2
public class FeatureResource {

    private final FeatureService featureService;

    public FeatureResource(final FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping(value = "/feature", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Feature> findAll() {
        return featureService.findAll();
    }

    @GetMapping(value = "/feature", params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Feature> findAllPaginated(@RequestParam("page") final int page, @RequestParam("size") final int size) {
        return featureService.findAllPaginated(page, size);
    }

    @GetMapping(value = "/feature/image/{id}")
    public ResponseEntity<byte[]> findPicturedById(@PathVariable final int id) {
        ImageData image = featureService.findImageById(id);
        MediaType mediaType = ContentTypeHelper.getMediaType(image.getImageExtension());
        return ResponseEntityHelper.responseForFile(image.getImage(), mediaType);
    }

    @GetMapping(value = "/feature/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Feature findById(@PathVariable final int id) {
        return featureService.findById(id);
    }

    @PostMapping(value = "/feature", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public Feature save(@Validated @ModelAttribute  final FeatureModelDTO model) {
        return featureService.save(this.convertModelToFeature(model));
    }
    @PutMapping(value = "/feature", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public Feature update(@Validated @ModelAttribute  final FeatureModelDTO model) {
        return featureService.update(this.convertModelToFeature(model));
    }

    @DeleteMapping(value = "/feature/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MAINTAINER')")
    public void delete(@PathVariable final int id) {
        featureService.delete(id);
    }

    private Feature convertModelToFeature(final FeatureModelDTO model) {
        var feature = new Feature();
        feature.setDescription(model.getDescription());
        feature.setTitle(model.getTitle());
        if (model.getId() > 0) {
            feature.setId(model.getId());
        }
        try {
            // Validate content type
            String contentType = model.getImage().getContentType();
            MediaType mediaType = ContentTypeHelper.getMediaType(Objects.requireNonNull(contentType));
            log.info("Valid media type {}/{}", mediaType.getType(), mediaType.getSubtype());
            feature.setImageExtension(contentType);
            feature.setImage(model.getImage().getBytes());
        } catch (IOException | NullPointerException e) {
            throw new BadRequestException("Invalid Image");
        }
        return feature;
    }
}
