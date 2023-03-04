package foot.footprint.controller.article;

import foot.footprint.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

//    @GetMapping("/test")
//    public ResponseEntity<Article> test() {
//        Article article = articleMapperRepository.selectAllByXml(1L);
//
//        return ResponseEntity.ok()
//                .body(article);
//    }
}
