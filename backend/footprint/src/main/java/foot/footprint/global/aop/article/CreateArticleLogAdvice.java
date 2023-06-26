package foot.footprint.global.aop.article;

import foot.footprint.domain.article.dto.CreateArticleRequest;
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
public class CreateArticleLogAdvice {

    @Pointcut("@annotation(foot.footprint.global.aop.article.CreateArticleLog)")
    public void createArticleLogRecord() {
    }

    @Around("createArticleLogRecord()")
    public Object createArticleLogRecord(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Object[] parameterValues = pjp.getArgs();
        List<Object> result = findPjpInfo(parameterValues, method);
        return printLog(result.get(0).toString(), (Long)result.get(1), method, pjp);
    }

    private Object printLog(String title , Long memberId, Method method, ProceedingJoinPoint pjp)
        throws Throwable {
        log.info("회원번호 " + memberId + "가 " + "게시글 '" + title + "' 작성을 시도하였습니다.");
        Object value = pjp.proceed();
        log.info("회원번호 " + memberId + "에 의해 " + "게시글 '" + title + "' 를 작성되었습니다.");
        return value;
    }

    private List<Object> findPjpInfo(Object[] parameterValues, Method method) {
        CreateArticleRequest request = null;
        Long memberId = null;
        for (int i = 0; i < parameterValues.length; i++) {
            if (method.getParameters()[i].getName().equals("request")) {
                request = (CreateArticleRequest) parameterValues[i];
                continue;
            }
            if (method.getParameters()[i].getName().equals("userDetails")) {
                CustomUserDetails userDetails = (CustomUserDetails) parameterValues[i];
                if (userDetails != null) {
                    memberId = userDetails.getId();
                }
            }
        }
        return Arrays.asList(request.getTitle(), memberId);
    }
}