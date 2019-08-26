package com.tom.cf.core.mobile;


public enum ResultCode {

	OK(200, ""),
	
	FAIL(400, "操作失败"),
	
	ILLEGAL_TOKEN(401, "无效token"),
	/**
	 * 非法参数
	 */
	ILLEGAL_ARGUMENT(406, "非法参数");

	private final int value;
	private final String reasonPhrase;

	private ResultCode(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}

	public static ResultCode valueOf(int statusCode) {
		for (ResultCode code : values()) {
			if (code.value == statusCode) {
				return code;
			}
		}
		throw new IllegalArgumentException("无法匹配 [" + statusCode + "]");
	}
	
	
	public int getValue() {
		return value;
	}
	
	public String getReasonPhrase() {
		return reasonPhrase;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("[value: ").append(value)
				.append(",reasonPhrase: ").append(reasonPhrase).append("]")
				.toString();
	}
}
