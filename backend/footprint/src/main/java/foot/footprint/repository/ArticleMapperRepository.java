package foot.footprint.repository;

import foot.footprint.domain.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleMapperRepository {

    @Select("select * from article where id=#{id}")
    Article selectAllArticle(Long id);

    Article selectAllByXml(Long id);
}
