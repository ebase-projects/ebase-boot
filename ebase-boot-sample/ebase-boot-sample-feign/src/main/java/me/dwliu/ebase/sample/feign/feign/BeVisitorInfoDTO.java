package me.dwliu.ebase.sample.feign.feign;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "来访登记表")
public class BeVisitorInfoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "访客登记")
	private Long id;

	@Schema(description = "来访类型：来访类型1、邀请普通访客 2、邀请家长 3、 终端自助预约 4、微信自助预约 5、周期访客")
	private Integer visitorType;

	@Schema(description = "学校id")
	private Integer schoolId;

	@Schema(description = "来访总人数")
	private Integer userCount;

	@Schema(description = "来访原因ID")
	private Integer visitorReasonId;

	@NotBlank(message = "来访事由不能为空")
	@Size(min = 1, max = 500, message = "来访原因应在{min}和{max}之间")
	@Schema(description = "来访原因")
	private String visitorReason;

	@Schema(description = "约见地址")
	@Size(min = 0, max = 500, message = "约见地址应在{min}和{max}之间")
	private String visitorAppointmentAddress;

	@Schema(description = "被访人")
	private Integer touserId;

	@Schema(description = "被访人名")
	private String touserName;

	@NotNull(message = "邀访开始时间不能为空")
	@Schema(description = "开始时间")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date visitorBeginTime;

	@Schema(description = "结束时间")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date visitorEndTime;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "创建时间")
	private Date createTime;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "更新时间")
	private Date updateTime;

	@Schema(description = "状态")
	private Integer status;

	@Schema(description = "版本号")
	private Integer version;

	@Schema(description = "是否删除")
	private Integer delFlag;


}
