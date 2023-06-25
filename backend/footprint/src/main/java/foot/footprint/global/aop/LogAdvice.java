package foot.footprint.global.aop;

import foot.footprint.global.security.user.CustomUserDetails;
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
public class LogAdvice {

    @Pointcut("@annotation(foot.footprint.global.aop.LogRecord)")
    public void logRecord() {
    }

    @Around("logRecord()")
    public Object LogRecord(ProceedingJoinPoint pjp) throws Throwable {
        Object[] parameterValues = pjp.getArgs();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Long memberId = null;
        for (int i = 0; i < parameterValues.length; i++) {
            if (method.getParameters()[i].getName().equals("userDetails")) {
                CustomUserDetails userDetails = (CustomUserDetails) parameterValues[i];
                if (userDetails != null) {
                    memberId = userDetails.getId();
                }
            }
        }
        log.info("회원번호: " + memberId + "가 " + method.getName() + "를 시도하였습니다.");
        Object value = pjp.proceed();
        log.info("회원번호: " + memberId + "에 의해 " + method.getName() + "가 실행되었습니다.");
        return value;
    }
}