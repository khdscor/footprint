package foot.footprint.global.aop.group;

import foot.footprint.domain.group.dto.CreateGroupRequest;
import foot.footprint.global.security.user.CustomUserDetails;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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
public class CreateGroupLogAdvice {

    @Pointcut("@annotation(foot.footprint.global.aop.group.CreateGroupLog)")
    public void createGroupLogRecord() {
    }

    @Around("createGroupLogRecord()")
    public Object createGroupLogRecord(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object[] parameterValues = pjp.getArgs();
        List<Object> result = findPjpInfo(parameterValues, method);
        return printLog((Long)result.get(0), result.get(1).toString(), pjp);
    }

    private Object printLog(Long memberId, String name, ProceedingJoinPoint pjp)
        throws Throwable {
        log.info("회원번호 " + memberId + "가 " + "그룹 '" + name + "' 생성을 시도하였습니다.");
        Object value = pjp.proceed();
        log.info("회원번호 " + memberId + "에 의해 " + "그룹 '" + name + "'가 생성되었습니다.");
        return value;
    }

    private List<Object> findPjpInfo(Object[] parameterValues, Method method) {
        CreateGroupRequest request = null;
        Long memberId = null;
        for (int i = 0; i < parameterValues.length; i++) {
            if (method.getParameters()[i].getName().equals("createGroupRequest")) {
                request = (CreateGroupRequest) parameterValues[i];
                continue;
            }
            if (method.getParameters()[i].getName().equals("userDetails")) {
                CustomUserDetails userDetails = (CustomUserDetails) parameterValues[i];
                if (userDetails != null) {
                    memberId = userDetails.getId();
                }
            }
        }
        return Arrays.asList(memberId, request.getGroupName());
    }
}