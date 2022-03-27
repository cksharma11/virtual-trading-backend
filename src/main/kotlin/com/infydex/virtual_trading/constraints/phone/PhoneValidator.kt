package com.infydex.virtual_trading.constraints.phone

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class PhoneValidator : ConstraintValidator<ValidPhone, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return value?.length == 10 && value.matches(Regex("(^$|[0-9]{10})"))
    }
}
