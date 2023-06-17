package com.experiment.quarkus.annotation.implementations;

import com.experiment.quarkus.annotation.annotations.DeviceValidator;
import com.experiment.quarkus.common.Device;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValidateDevice implements ConstraintValidator<DeviceValidator, String> {

    @Override
    public boolean isValid(String capturedBy, ConstraintValidatorContext context) {
        return Arrays.stream(Device.values()).map(Enum::name).toList().contains(capturedBy);
    }
}
