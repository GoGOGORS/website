package com.nearze.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * RuiXiang
 * 2020-04-02-16-28
 * @author: RuiXiang
 */

@Data
@ToString
public class ResultBody<T> implements Serializable {

    private static final long serialVersionUID = -6557898536448299915L;

    @ApiModelProperty(value = "仅表示接口调用状态，1 成功，0 失败", required = true)
    private Integer result;//仅表示接口调用状态，1 成功，0 失败
    private T data;//接口返回数据封装类或集合
    private String msg;//异常信息提示

    public static final int SUCCESS_CODE = 1;
    public static final int ERROR_CODE = 0;
    public static final int RE_LOGIN_CODE = 506;

    public static <T> ResultBody<T> success()
    {
        ResultBody<T> response = new ResultBody<>();
        response.setResult(SUCCESS_CODE);
        response.setMsg("success");
        return response;
    }

    public static <T> ResultBody<T> success(T data)
    {
        ResultBody<T> response = new ResultBody<>();
        response.setResult(SUCCESS_CODE);
        response.setMsg("success");
        response.setData(data);
        return response;
    }

    public static <T> ResultBody<T> error(String message)
    {
        ResultBody<T> response = new ResultBody<>();
        response.setResult(ERROR_CODE);
        response.setMsg(message);
        return response;
    }

    public static <T> ResultBody<T> reLogin(String message)
    {
        ResultBody<T> response = new ResultBody<>();
        response.setResult(RE_LOGIN_CODE);
        response.setMsg(message);
        return response;
    }

    public ResultBody(){}

    /**
     * api消息
     */
    public enum ResponseMsg
    {
        SUCCESS("操作成功"),
        FAIL("操作失败"),
        TOKEN_ERROR("token失效，请重新登陆"),
        INVALID_CLIENT_ID("无效的clientID"),
        PARAMETER_ERROR("参数为空或参数有误"),
        NOT_LOGIN("用户未登陆"),
        UNEXIST_USER("用户不存在"),
        LOGOUT_ERROR("注销失败"),
        SERVICE_UNAVAILABLE("无法访问服务，该服务可能由于某种未知原因被关闭，请重启服务！"),
        EXCEPTION("接口发生异常"),
        TIMEOUT("执行超时"),
        HYSTRIX_EXCEPTION("发生异常，进入熔断处理"),
        ID_CARD_NUMBER_EXCEPTION("身份证格式错误");

        private String value;

        public String getValue(){
            return value;
        }

        public void setValue(String value){
            this.value = value;
        }

        ResponseMsg(String value){
            this.value = value;
        }
    }

}
