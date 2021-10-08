package com.marchsoft.secondkill.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于前端Ajax请求后端时，后端给前端返回结果
 *
 * @author Yangluxin
 */

public class AjaxResponse {

    public static final String ERROR_MSG = "操作失败";

    public static final String SUCCESS_MSG = "success";

    public static final int SUCCESS_NO = 200;

    public static final int FAILED = 500;

//    public static final int SELECT_NULL = 100001;

//    public static final String SELECT_NULL_MSG = "The query result is empty";

    private int errorNo;
    private String errorInfo;
    private Object data;

    private AjaxResponse() {
    }

    public static AjaxResponse newInstance() {
        return new AjaxResponse();
    }

    public static AjaxResponse newInstance(int errorNo, String errorInfo) {
        AjaxResponse response = new AjaxResponse();
        response.setErrorNo(errorNo);
        response.setErrorInfo(errorInfo);
        response.setData(new HashMap<String, Object>());
        return response;
    }

    public static AjaxResponse newSuccess() {
        return newInstance(SUCCESS_NO, SUCCESS_MSG);
    }

    public static AjaxResponse new403() {
        return newInstance(403, "Permission denied");
    }

    public static AjaxResponse new401() {
        return newInstance(401, "Login required");
    }

    public int getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(int errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void putData(String key, Object value) {
        if (data == null || !(data instanceof Map)) {
            data = new HashMap<String, Object>();
        }
        ((Map) data).put(key, value);
    }

    public void setException(Exception e) {
        if (null != e) {
            this.setErrorNo(FAILED);
            this.setErrorInfo(e.getMessage());
        }
    }
}
