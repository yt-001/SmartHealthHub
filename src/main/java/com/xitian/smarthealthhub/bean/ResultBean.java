package com.xitian.smarthealthhub.bean;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 全局统一响应对象，用于封装接口返回结果。
 * 包含状态码、提示信息和业务数据三部分。
 * 支持链式调用和静态工厂方法创建实例。
 *
 * @param <T> 业务数据类型
 */
@Schema(description = "全局统一响应对象")
public class ResultBean<T> implements Serializable {

    @Schema(description = "状态码", example = "200")
    private int code;

    @Schema(description = "提示信息", example = "成功")
    private String msg;

    @Schema(description = "业务数据")
    private T data;

    /* -------- 私有构造，只允许静态工厂 -------- */

    /**
     * 私有构造函数，防止外部直接实例化。
     */
    private ResultBean() {}

    /* -------- 静态工厂 -------- */

    /**
     * 创建一个表示操作成功的响应对象，默认无业务数据。
     *
     * @param <T> 业务数据类型
     * @return 成功的响应对象
     */
    public static <T> ResultBean<T> success() {
        return success(null);
    }

    /**
     * 创建一个表示操作成功的响应对象，并携带指定的业务数据。
     *
     * @param data 业务数据
     * @param <T>  业务数据类型
     * @return 成功的响应对象
     */
    public static <T> ResultBean<T> success(T data) {
        return of(StatusCode.SUCCESS, data);
    }

    /**
     * 创建一个表示操作失败的响应对象，使用预定义的状态码。
     *
     * @param status 状态码枚举
     * @param <T>    业务数据类型
     * @return 失败的响应对象
     */
    public static <T> ResultBean<T> fail(StatusCode status) {
        return of(status, null);
    }

    /**
     * 创建一个表示操作失败的响应对象，使用自定义的消息覆盖默认消息。
     *
     * @param status      状态码枚举
     * @param overrideMsg 自定义错误信息
     * @param <T>         业务数据类型
     * @return 失败的响应对象
     */
    public static <T> ResultBean<T> fail(StatusCode status, String overrideMsg) {
        ResultBean<T> rb = new ResultBean<>();
        rb.code = status.getCode();
        rb.msg = overrideMsg;
        return rb;
    }

    /**
     * 根据给定的状态码和业务数据创建响应对象。
     *
     * @param status 状态码枚举
     * @param data   业务数据
     * @param <T>    业务数据类型
     * @return 响应对象
     */
    public static <T> ResultBean<T> of(StatusCode status, T data) {
        ResultBean<T> rb = new ResultBean<>();
        rb.code = status.getCode();
        rb.msg = status.getMessage();
        rb.data = data;
        return rb;
    }

    /* -------- 链式二次封装（可选） -------- */

    /**
     * 设置提示信息并支持链式调用。
     *
     * @param msg 提示信息
     * @return 当前响应对象
     */
    public ResultBean<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 设置业务数据并支持链式调用。
     *
     * @param data 业务数据
     * @return 当前响应对象
     */
    public ResultBean<T> data(T data) {
        this.data = data;
        return this;
    }

    /* -------- getter -------- */

    /**
     * 获取状态码。
     *
     * @return 状态码
     */
    public int getCode() { return code; }

    /**
     * 获取提示信息。
     *
     * @return 提示信息
     */
    public String getMsg() { return msg; }

    /**
     * 获取业务数据。
     *
     * @return 业务数据
     */
    public T getData() { return data; }

    private static final long serialVersionUID = 1L;
}
