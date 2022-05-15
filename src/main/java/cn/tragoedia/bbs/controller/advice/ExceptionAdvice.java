package cn.tragoedia.bbs.controller.advice;

import cn.tragoedia.bbs.utils.Result;
import cn.tragoedia.bbs.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Result handleException(Exception e) {
        log.error("服务器发生异常: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            log.error(element.toString());
        }
        return ResultUtils.Failure("服务器异常" + e.getMessage());
    }
}
