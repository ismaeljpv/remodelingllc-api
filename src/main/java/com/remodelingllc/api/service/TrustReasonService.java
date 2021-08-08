package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.TrustReason;
import com.remodelingllc.api.exception.EntityNotFoundException;
import com.remodelingllc.api.interfaces.ImageData;
import com.remodelingllc.api.repository.TrustReasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustReasonService {

    private final TrustReasonRepository trustReasonRepository;

    public TrustReasonService(final TrustReasonRepository trustReasonRepository) {
        this.trustReasonRepository = trustReasonRepository;
    }

    public List<TrustReason> findAll() {
        return (List<TrustReason>) trustReasonRepository.findAll();
    }

    public TrustReason findById(final int id) {
        var oldReason = trustReasonRepository.findById(id);
        if (oldReason.isEmpty()) {
            throw new EntityNotFoundException("Trust Reason Not Found");
        }
        return oldReason.get();
    }

    public ImageData findImageById(final int id) {
        var oldReason = trustReasonRepository.findById(id);
        if (oldReason.isEmpty()) {
            throw new EntityNotFoundException("Trust Reason Not Found");
        }
        return trustReasonRepository.findImageById(id);
    }

    public TrustReason save(final TrustReason reason) {
        return trustReasonRepository.save(reason);
    }

    public TrustReason update(final TrustReason reason) {
        var oldReason = trustReasonRepository.findById(reason.getId());
        if (oldReason.isEmpty()) {
            throw new EntityNotFoundException("Trust Reason Not Found");
        }
        return trustReasonRepository.save(reason);
    }

    public void delete(final int id) {
        var oldReason = trustReasonRepository.findById(id);
        if (oldReason.isEmpty()) {
            throw new EntityNotFoundException("Trust Reason Not Found");
        }
        trustReasonRepository.delete(oldReason.get());
    }
}
