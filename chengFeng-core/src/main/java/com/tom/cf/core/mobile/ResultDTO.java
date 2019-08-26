package com.tom.cf.core.mobile;

public class ResultDTO<T> {
	
	private int resultCode;
	private String resultMsg;
//	private ResultCode resultCode;	//fastjson toJSONString无法直接序列化枚举得到想要的格式
	private T data;

	public ResultDTO() {
	}

	public ResultDTO(ResultCode resultCode) {
		this(resultCode, null);
	}

	public ResultDTO(T data) {
		this(ResultCode.OK, data);
	}

	public ResultDTO(ResultCode resultCode, T data) {
		this.resultCode = resultCode.getValue();
		this.resultMsg = resultCode.getReasonPhrase();
		this.data = data;
	}
	

	public int getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}
	
	public ResultDTO<T> setResultCode(ResultCode resultCode){
		this.resultCode = resultCode.getValue();
		this.resultMsg = resultCode.getReasonPhrase();
		return this;
	}

	public T getData() {
		return data;
	}

	public ResultDTO<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	public ResultDTO<T> setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
		return this;
	}

	public static <T>ResultDTO<T> newInstance(){
		return new ResultDTO<T>(ResultCode.OK);
	}

}
