package com.remodelingllc.api.service;

import com.remodelingllc.api.entity.Feature;
import com.remodelingllc.api.exception.BadRequestException;
import com.remodelingllc.api.interfaces.ImageData;
import com.remodelingllc.api.repository.FeatureRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureService {

   private FeatureRepository featureRepository;

    public FeatureService(final FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> findAll(){
        return (List<Feature>) featureRepository.findAll();
    }

    public Page<Feature> findAllPaginated(final int page, final int size) {
        return featureRepository.findAll(PageRequest.of(page, size));
    }

    public Feature findById(final int id) {
        var feature = featureRepository.findById(id);
        if (feature.isEmpty()) {
            throw new BadRequestException("Feature Not Found");
        }
        return feature.get();
    }

    public ImageData findImageById(final int id) {
        var feature = featureRepository.findById(id);
        if (feature.isEmpty()) {
            throw new BadRequestException("Feature Not Found");
        }
        return featureRepository.findImageById(id);
    }

    public Feature save(final Feature feature){
        return featureRepository.save(feature);
    }

    public Feature update(final Feature feature) {
        var oldFeature = featureRepository.findById(feature.getId());
        if (oldFeature.isEmpty()) {
            throw new BadRequestException("Feature Not Found");
        }
        return featureRepository.save(feature);
    }

    public void delete(final int id) {
        var oldFeature = featureRepository.findById(id);
        if (oldFeature.isEmpty()) {
            throw new BadRequestException("Feature Not Found");
        }
        featureRepository.delete(oldFeature.get());
    }
}
