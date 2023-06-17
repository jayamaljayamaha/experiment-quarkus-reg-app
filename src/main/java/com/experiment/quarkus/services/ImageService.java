package com.experiment.quarkus.services;

import com.experiment.quarkus.common.Device;
import com.experiment.quarkus.common.InvalidImage;
import com.experiment.quarkus.common.ReturnImage;
import com.experiment.quarkus.dto.ImageData;
import com.experiment.quarkus.dto.ImageRequest;
import com.experiment.quarkus.dto.ImageResponse;
import com.experiment.quarkus.entity.Image;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

@ApplicationScoped
public class ImageService {

    @Inject
    EntityManager entityManager;

    @Inject
    Validator validator;

    @Transactional
    public ImageResponse saveImages(ImageRequest imageRequest) {
        ImageResponse response = ImageResponse.builder().build();
        imageRequest.getImages().stream()
                .filter(imageData -> this.validateImageData(imageData, response, imageRequest.getImages().indexOf(imageData)))
                .map(this::createImageEntity)
                .forEach(image -> {
                    entityManager.persist(image);
                    entityManager.flush();
                    response.getImages().add(ReturnImage.builder()
                            .id(image.getId())
                            .url(image.getUrl())
                            .name(image.getName())
                            .build());
                    response.setSuccessImages(response.getSuccessImages() + 1);
                });
        return response;
    }

    private boolean validateImageData(ImageData imageData, ImageResponse imageResponse, int index) {
        Set<ConstraintViolation<ImageData>> violations = validator.validate(imageData);
        if (!violations.isEmpty()) {
            imageResponse.setFailedImages(imageResponse.getFailedImages() + 1);
            imageResponse.getInvalidImages().addAll(violations.stream()
                    .map(violation -> InvalidImage.builder().property(violation.getPropertyPath().toString())
                            .imageIndex(index).error(violation.getMessage()).build()).toList());
        }
        return violations.isEmpty();
    }

    private Image createImageEntity(ImageData imageData) {
        return Image.builder()
                .name(imageData.getName())
                .url(imageData.getUrl())
                .width(imageData.getWidth())
                .height(imageData.getHeight())
                .numberOfPixels(imageData.getNumberOfPixels())
                .format(imageData.getFormat())
                .createdDate(imageData.getCreatedDate())
                .lastModifiedDate(imageData.getLastModifiedDate())
                .size(imageData.getSize())
                .capturedBy(imageData.getCapturedBy())
                .device(Device.valueOf(imageData.getDevice()))
                .build();
    }
}
