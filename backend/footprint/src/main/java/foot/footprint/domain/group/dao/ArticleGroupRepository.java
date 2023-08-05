package foot.footprint.domain.group.dao;

import foot.footprint.domain.group.domain.ArticleGroup;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleGroupRepository {

    int saveArticleGroupList(List<ArticleGroup> articleGroups);

    int deleteArticleGroup(Long memberId, Long groupId);

    boolean existsArticleInMyGroup(Long articleId, Long memberId);

    @Select("select * from Article_group order by id")
    List<ArticleGroup> findAll();
}