package foot.footprint.repository.featureFactory;

import static org.jeasy.random.FieldPredicates.*;

import foot.footprint.domain.article.domain.Article;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.range.DoubleRangeRandomizer;

public class ArticleFeatureFactory {

  static public EasyRandom create(Long memberId) {
    Predicate<Field> articleIdPredicate = named("id").and(ofType(Long.class))
        .and(inClass(Article.class));
    Predicate<Field> memberIdIdPredicate = named("member_id").and(ofType(Long.class))
        .and(inClass(Article.class));
    EasyRandomParameters param = new EasyRandomParameters().excludeField(articleIdPredicate)
        .dateRange(LocalDate.of(2022, 5, 1), LocalDate.of(2023, 7, 22))
        .stringLengthRange(4, 46)
        .randomize(Double.class, new DoubleRangeRandomizer(-175.0, 175.0))
        .randomize(memberIdIdPredicate, () -> memberId);
    return new EasyRandom(param);
  }
}
