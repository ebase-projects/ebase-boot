package me.dwliu.lab.common.validator.annotation;

import me.dwliu.lab.common.exception.BusinessException;
import me.dwliu.lab.common.code.SystemResultCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

/**
 * 校验枚举值有效性
 * <p>使用方法</p>
 * <p>@EnumValue(enumClass=UserStatusEnum.class, enumMethod="isValidName")</p>
 * <p>
 *
 * </p>
 *
 * @author liudw
 * @date 2018/7/11 17:21
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValid.Validator.class)
public @interface EnumValid {

    String message() default "无效的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String enumMethod();

    class Validator implements ConstraintValidator<EnumValid, Object> {

        private Class<? extends Enum<?>> enumClass;
        private String enumMethod;

        @Override
        public void initialize(EnumValid enumValid) {
            enumMethod = enumValid.enumMethod();
            enumClass = enumValid.enumClass();
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return Boolean.TRUE;
            }

            if (enumClass == null || enumMethod == null) {
                return Boolean.TRUE;
            }

            Class<?> valueClass = value.getClass();

            try {
                Method method = enumClass.getMethod(enumMethod, valueClass);
                if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                    throw new BusinessException(SystemResultCode.FAILURE, String.format("%s method return is not boolean type in the %s class", enumMethod, enumClass));
                }

                if (!Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException(String.format("%s method is not static method in the %s class", enumMethod, enumClass));
                }

                Boolean result = (Boolean) method.invoke(null, value);
                return result == null ? false : result;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
            }
        }

    }
}
