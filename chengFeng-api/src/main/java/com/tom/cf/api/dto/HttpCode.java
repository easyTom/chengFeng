package com.tom.cf.api.dto;

/**
 * @method: Http状态码
 */
public enum HttpCode {
    /** 200请求成功 */
    OK(200,"请求成功"),
    /** 207频繁操作 */
    MULTI_STATUS(207,"频繁操作"),
    /** 303登录失败 */
    LOGIN_FAIL(303,"登录失败"),
    /** 400请求参数出错 */
    BAD_REQUEST(400,"请求参数出错"),
    /** 401没有登录 */
    UNAUTHORIZED(401,"没有登录"),
    /** 403没有权限 */
    FORBIDDEN(403,"没有权限"),
    /** 404找不到页面 */
    NOT_FOUND(404,"找不到页面 "),
    /** 408请求超时 */
    REQUEST_TIMEOUT(408,"请求超时"),
    /** 409发生冲突 */
    CONFLICT(409,"发生冲突"),
    /** 410已被删除 */
    GONE(410,"已被删除"),
    /** 423已被锁定 */
    LOCKED(423,"已被锁定"),
    /*检查表单是否存在 */
    CHECK(300,"新增的表单已经存在"),
    /** 删除信息被检查部位占用无法删除*/
    DELETE(301,"删除信息在检查部位中被占用"),
    /**删除路径被占用*/
    INSTANCE_DELETE(302,"该目录下存放了影像文件，不能删除"),
    /** 500服务器出错 */
    INTERNAL_SERVER_ERROR(500,"服务器出错"),

    INTERNAL_SERVER_DISK_FULL_ERROR(501,"服务器存储空间不足");

    private final Integer value;
    private String msg;

    private HttpCode(Integer value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    /**
     * Return the integer value of this status code.
     */
    public Integer value() {
        return this.value;
    }

    public String msg() {
        return this.msg;
    }

    public String toString() {
        return this.value.toString();
    }
}
