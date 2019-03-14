package com.tom.cf.api.dto;

/**
 * @method: 封装返回结果格式
 */

public class ResultDTO<T> {

    private int resultCode;
    private String resultMsg;
    //	private ResultCode resultCode;	//fastjson toJSONString无法直接序列化枚举得到想要的格式
    private T data;

    public ResultDTO() {
    }

    public ResultDTO(HttpCode resultCode) {
        this(resultCode, null);
    }

    public ResultDTO(T data) {
        this(HttpCode.OK, data);
    }

    public ResultDTO(HttpCode resultCode, T data) {
        this.resultCode = resultCode.value();
        this.resultMsg = resultCode.msg();
        this.data = data;
    }


    public int getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public ResultDTO<T> setResultCode(HttpCode resultCode){
        this.resultCode = resultCode.value();
        this.resultMsg = resultCode.msg();
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
        return new ResultDTO<T>(HttpCode.OK);
    }

}
