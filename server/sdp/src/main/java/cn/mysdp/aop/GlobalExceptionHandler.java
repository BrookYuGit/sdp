package cn.mysdp.aop;

import cn.mysdp.http.SDPHttpException;
import cn.mysdp.http.SDPHttpResponse;
import cn.mysdp.http.SDPHttpResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.InvocationTargetException;


/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(value = HttpStatus.OK)
    public SDPHttpResponse<Object> handleCommonException(Throwable e) throws Exception {
        if (e instanceof InvocationTargetException && ((InvocationTargetException) e).getTargetException() != null) {
            System.out.println("======== InvocationTargetException ========");
            e = ((InvocationTargetException) e).getTargetException();
        }
        System.out.println("======== " + e.getMessage() + " ========");
        e.printStackTrace();
        System.out.println("--------");
        return SDPHttpResponse.builder()
                    .code(SDPHttpResult.FAIL.getCode())
                    .msg(e.getMessage())
                    .build();

    }

    @ExceptionHandler(value = {SDPHttpException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public SDPHttpResponse<Object> handleHttpException(SDPHttpException e)
    {
        System.out.println("======== " + e.getMessage() + " ========");
        e.printStackTrace();
        System.out.println("--------");
        return SDPHttpResponse.builder()
                .code(e.getError().getCode())
                .msg(e.getMessage())
                .body("")
                .build();
    }

}
