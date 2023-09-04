package foot.footprint.featureFactory;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import foot.footprint.domain.member.domain.Member;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFeatureFactory {

    static public EasyRandom create(Long id) {
        Predicate<Field> memberIdPredicate = named("id").and(ofType(Long.class))
            .and(inClass(Member.class));
        EasyRandomParameters param = new EasyRandomParameters()
            .dateRange(LocalDate.of(2022, 5, 1), LocalDate.of(2023, 7, 22))
            .stringLengthRange(4, 46);
        if (id < 0) {
            param.excludeField(memberIdPredicate);
            return new EasyRandom(param);
        }
        param.randomize(memberIdPredicate, () -> id);
        return new EasyRandom(param);
    }
}