package com.sanka.mailservice.validation;

import javax.mail.internet.InternetAddress;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/***
 * IETF RFC-822 stipulates the criteria and regex for a valid email address.
 * The Email annotation provided by javax validation is insufficient.
 *
 * @author moonlander70
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public void initialize(final ValidEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        try {
            final InternetAddress emailAddr = new InternetAddress(value);
            emailAddr.validate();
            return true;
        } catch (final Exception ex){
            return false;
        }
    }
}
