package me.dwliu.ebase.sample.feign.feign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;


/**
 * 来访登记表
 *
 * @author liudw
 * @date 2019-07-11 10:57
 **/
@Data
@ApiModel(value = "来访登记表")
public class BeVisitorInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "访客登记")
    private Long id;

    @ApiModelProperty(value = "来访类型：来访类型1、邀请普通访客 2、邀请家长 3、 终端自助预约 4、微信自助预约 5、周期访客")
    private Integer visitorType;

    @ApiModelProperty(value = "学校id")
    private Integer schoolId;

    @ApiModelProperty(value = "来访总人数")
    private Integer userCount;

    @ApiModelProperty(value = "来访原因ID")
    private Integer visitorReasonId;

    @NotBlank(message = "来访事由不能为空")
    @Size(min = 1, max = 500, message = "来访原因应在{min}和{max}之间")
    @ApiModelProperty(value = "来访原因")
    private String visitorReason;

    @ApiModelProperty(value = "约见地址")
    @Size(min = 0, max = 500, message = "约见地址应在{min}和{max}之间")
    private String visitorAppointmentAddress;

    @ApiModelProperty(value = "被访人")
    private Integer touserId;

    @ApiModelProperty(value = "被访人名")
    private String touserName;

    @NotNull(message = "邀访开始时间不能为空")
    @ApiModelProperty(value = "开始时间")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date visitorBeginTime;

    @ApiModelProperty(value = "结束时间")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date visitorEndTime;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "版本号")
    private Integer version;

    @ApiModelProperty(value = "是否删除")
    private Integer delFlag;


}
