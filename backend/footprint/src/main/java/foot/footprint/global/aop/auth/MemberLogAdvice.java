package foot.footprint.global.aop.auth;

import foot.footprint.domain.member.dto.LoginRequest;
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
public class MemberLogAdvice {

    @Pointcut("@annotation(foot.footprint.global.aop.auth.MemberLog)")
    public void memberLogRecord() {
    }

    @Around("memberLogRecord()")
    public Object loginLogRecord(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object[] parameterValues = pjp.getArgs();
        Long memberId = findPjpInfo(parameterValues, method);
        return printLog(memberId, pjp);
    }

    private Object printLog(Long memberId, ProceedingJoinPoint pjp)
        throws Throwable {
        log.info("회원번호 " + memberId + "가 회원탈퇴를 시도하였습니다.");
        Object value = pjp.proceed();
        log.info("회원번호 " + memberId + "가 회원탈퇴를 성공하였습니다.");
        return value;
    }

    private Long findPjpInfo(Object[] parameterValues, Method method) {
        Long memberId = null;
        for (int i = 0; i < parameterValues.length; i++) {
            if (method.getParameters()[i].getName().equals("userDetails")) {
                CustomUserDetails userDetails = (CustomUserDetails) parameterValues[i];
                if (userDetails != null) {
                    memberId = userDetails.getId();
                }
            }
        }
        return memberId;
    }
}