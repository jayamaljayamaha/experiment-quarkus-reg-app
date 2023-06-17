package com.experiment.quarkus.dto;

import lombok.Data;

import java.util.List;

@Data
public class ImageRequest {
    private List<ImageData> images;
}
