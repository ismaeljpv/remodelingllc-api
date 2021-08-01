package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Services;
import com.remodelingllc.api.entity.enums.Status;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.interfaces.ThumbnailData;
import com.remodelingllc.api.repository.ServicesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicesService {

    private final ServicesRepository servicesRepository;

    public ServicesService(final ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public List<Services> findAll() {
        return servicesRepository.findAllByStatus(Status.ACTIVE);
    }

    public Page<Services> findAllActive(final int page, final int size) {
        return servicesRepository.findAllByStatus(Status.ACTIVE, PageRequest.of(page, size));
    }

    public Services findById(final int id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty() || service.get().getStatus() == Status.INACTIVE) {
            throw new EntityNotFoundException("Service Not Found");
        }
        return service.get();
    }

    public ThumbnailData findThumbnailById(final int id){
        return servicesRepository.findThumbnailById(id);
    }

    public Services save(final Services services) {
        return servicesRepository.save(services);
    }

    public Services update(final Services services) {
        Optional<Services> oldService = servicesRepository.findById(services.getId());
        if (oldService.isEmpty()) {
            throw new EntityNotFoundException("Service Not Found");
        }
        return servicesRepository.save(services);
    }

    public void delete(final int id) {
        Optional<Services> service = servicesRepository.findById(id);
        if (service.isEmpty()) {
            throw new EntityNotFoundException("Service Not Found");
        }
        service.get().setStatus(Status.INACTIVE);
        service.get().setThumbnail(null);
        servicesRepository.save(service.get());
    }
}
