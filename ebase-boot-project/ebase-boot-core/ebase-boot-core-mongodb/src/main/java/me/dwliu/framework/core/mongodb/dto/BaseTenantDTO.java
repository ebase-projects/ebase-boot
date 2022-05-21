package me.dwliu.framework.core.mongodb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liudw
 * @date 2019-08-26 15:15
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseTenantDTO extends BaseDTO {

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private String tenantCode;

}
