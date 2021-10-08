package com.marchsoft.secondkill.common.handler;

import com.marchsoft.secondkill.vo.AjaxResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * @author DELL
 * @date 2020/8/10 10:32
 */
@ControllerAdvice
public class MSExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(MSExceptionHandler.class);

    /**
     * @description: 异常处理
     * @param e
     * @return: com.winner.vo.AjaxResponse
     * @author: sangjinchao
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public AjaxResponse errorResult(RuntimeException e) {
        logger.error("程序发生异常，异常原因：" + e.toString());
        if (e instanceof MethodArgumentTypeMismatchException) {
            return AjaxResponse.newInstance(10001, "参数错误，请检查参数");
        }
        return AjaxResponse.newInstance(500, e.toString());
    }
}
