package com.tom.cf.core.mobile;

import com.tom.cf.core.entity.User;
import com.tom.cf.core.utils.IdGen;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "cf_auth")
public class AuthEntity {
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@GeneratedValue(generator = "jpa-uuid")
	@Id
	@Column(name = "id", length = 50, columnDefinition = "varchar(50) COMMENT '主键'")
	private String id;

	@Column(name = "token", length = 50, columnDefinition = "varchar(50) COMMENT '令牌验证'")
	private String token;

	@Column(name = "user_id", length = 50, columnDefinition = "varchar(50) COMMENT '用户Id'")
	private String userId;

	@Column(name = "user_name", length = 50, columnDefinition = "varchar(50) COMMENT '令牌验证'")
	private String userName;

	@Column(name = "status", length = 50, columnDefinition = "varchar(50) COMMENT  '是否锁定'")
	private String status;

	@Column(name = "device", length = 50, columnDefinition = "varchar(50) COMMENT 'imei'")
	private String device;

	@Column(name = "app_key", length = 50, columnDefinition = "varchar(50) COMMENT '区分是电脑还是手机'")
	private String appKey;

	@Column(name = "create_at",columnDefinition="datetime COMMENT '创建时间'")
	private Date createTime = new Date();

	
	/**
	 * 验证授权是否有效
	 */
	public boolean valid() {
		return !Satus.LOCKED.name().equalsIgnoreCase(status);
	}
	
	public static AuthEntity buildFrom(LoginModel login, TokenDTO token, User user){
		AuthEntity auth = new AuthEntity();
		auth.setUserName(login.getAccount());
		auth.setDevice(login.getDevice());
		auth.setId(IdGen.uuid());
		auth.setToken(token.getToken());
		auth.setUserId(user.getUserId());
		auth.setAppKey(login.getAppKey());
		return auth;
	}
	
	enum Satus{
		LOCKED
	}
}
