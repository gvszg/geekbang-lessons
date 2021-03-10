package org.geektimes.projects.user.validator.bean.validation;

import org.apache.commons.lang.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidAnnotationValidator implements ConstraintValidator<PhoneValid, String> {
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }

        return phone.matches("^1[0-9]{10}$");
    }
}
