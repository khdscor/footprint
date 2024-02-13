package foot.footprint.featureFactory;

import foot.footprint.domain.group.domain.Group;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;

import static org.jeasy.random.FieldPredicates.*;

public class GroupFeatureFactory {

    static public EasyRandom create(Long ownerId) {
        Predicate<Field> ownerIdPredicate = named("owner_id").and(ofType(Long.class))
                .and(inClass(Group.class));
        EasyRandomParameters param = new EasyRandomParameters()
            .dateRange(LocalDate.of(2022, 5, 1), LocalDate.of(2024, 2, 3))
                .randomize(Long.class, new LongRangeRandomizer(0L, 100000000L))
                .stringLengthRange(4, 10)
            .randomize(ownerIdPredicate, () -> ownerId);
        return new EasyRandom(param);
    }
}