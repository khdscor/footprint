package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.ArticleGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleGroupRepository {

    int saveArticleGroupList(List<ArticleGroup> articleGroups);

    boolean existsArticleInMyGroup(Long articleId, Long memberId);
}