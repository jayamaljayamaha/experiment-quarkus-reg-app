package com.experiment.quarkus.exception.exceptions;

import com.experiment.quarkus.common.InvalidImage;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidImageException extends RuntimeException{

    private List<InvalidImage> invalidImageList;

    public InvalidImageException(List<InvalidImage> invalidImageList){
        this.invalidImageList = invalidImageList;
    }

    public List<InvalidImage> getInvalidImageList() {
        return invalidImageList;
    }
}
