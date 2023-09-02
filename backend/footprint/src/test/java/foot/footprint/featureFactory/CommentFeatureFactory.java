package foot.footprint.featureFactory;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.named;
import static org.jeasy.random.FieldPredicates.ofType;

import foot.footprint.domain.comment.domain.Comment;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class CommentFeatureFactory {

    static public EasyRandom create(Long commentId, Long articleId, Long memberId) {
        Predicate<Field> commentIdPredicate = named("id").and(ofType(Long.class))
            .and(inClass(Comment.class));
        Predicate<Field> memberIdPredicate = named("member_id").and(ofType(Long.class))
            .and(inClass(Comment.class));
        Predicate<Field> articleIdPredicate = named("article_id").and(ofType(Long.class))
            .and(inClass(Comment.class));
        EasyRandomParameters param = new EasyRandomParameters()
            .dateRange(LocalDate.of(2022, 5, 1), LocalDate.of(2023, 7, 22))
            .stringLengthRange(4, 46)
            .randomize(articleIdPredicate, () -> articleId)
            .randomize(memberIdPredicate, () -> memberId);
        if(commentId >= 0) {
            param.excludeField(commentIdPredicate);
        }
        return new EasyRandom(param);
    }
}