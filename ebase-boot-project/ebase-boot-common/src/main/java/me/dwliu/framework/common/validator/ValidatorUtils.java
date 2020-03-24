package me.dwliu.framework.common.validator;


import me.dwliu.framework.common.code.SystemResultCode;
import me.dwliu.framework.common.exception.BusinessException;
import me.dwliu.framework.common.model.Result;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * hibernate-validator校验工具类
 * <p>
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author liudw
 * @email ldw4033 at 163.com
 * @date 2017-03-15 10:50
 */
//@Slf4j
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

//    /**
//     * 校验对象
//     *
//     * @param object 待校验对象
//     * @param groups 待校验的组
//     * @throws BusinessException 校验不通过，则报RRException异常
//     */
//    public static void validateEntity(Object object, Class<?>... groups)
//            throws BusinessException {
//        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
//        if (!constraintViolations.isEmpty()) {
//            ConstraintViolation<Object> constraint = (ConstraintViolation<Object>) constraintViolations.iterator().next();
//            throw new BusinessException(SystemResultCode.FAILURE, constraint.getMessage());
//        }
//
//
//    }

    /**
     * 校验对象
     *
     * @param object 待校验对象
     * @param groups 待校验的组
     * @throws BusinessException 校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws BusinessException {
        Set<ConstraintViolation<Object>> result = validator.validate(object, groups);
        List<ParameterInvalidItem> parameterInvalidItemList = new ArrayList<>();
        if (!result.isEmpty()) {

            for (Iterator<ConstraintViolation<Object>> it = result.iterator(); it.hasNext(); ) {
                ParameterInvalidItem parameterInvalidItem = new ParameterInvalidItem();
                ConstraintViolation<Object> violation = it.next();
                parameterInvalidItem.setFieldName(violation.getPropertyPath() + "");
                parameterInvalidItem.setMessage(violation.getMessage());
                parameterInvalidItemList.add(parameterInvalidItem);
            }


            throw new CustomMethodArgumentNotValidException(Result.fail(SystemResultCode.PARAM_VALID_ERROR, "参数验证错误", parameterInvalidItemList));
        }


    }
}
