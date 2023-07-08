package foot.footprint.global.aop.auth;

import foot.footprint.domain.member.dto.authRequest.LoginRequest;
import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginLogAdvice {

    @Pointcut("@annotation(foot.footprint.global.aop.auth.LoginLog)")
    public void loginLogRecord() {
    }

    @Around("loginLogRecord()")
    public Object loginLogRecord(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object[] parameterValues = pjp.getArgs();
        String email = findPjpInfo(parameterValues, method);
        return printLog(email, pjp);
    }

    private Object printLog(String email, ProceedingJoinPoint pjp)
        throws Throwable {
        log.info("임의의 사용자가 이메일 '" + email +  "'로 로그인을 시도하였습니다.");
        Object value = pjp.proceed();
        log.info("임의의 사용자가 이메일 '" + email +  "'로 로그인을 성공하였습니다.");
        return value;
    }

    private String findPjpInfo(Object[] parameterValues, Method method) {
        String email = null;
        for (int i = 0; i < parameterValues.length; i++) {
            if (method.getParameters()[i].getName().equals("loginRequest")) {
                LoginRequest request = (LoginRequest) parameterValues[i];
                if (request != null) {
                    email = request.getEmail();
                }
            }
        }
        return email;
    }
}