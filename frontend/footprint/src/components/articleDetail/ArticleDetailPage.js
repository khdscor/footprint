import React, { useEffect, useState } from "react";
import WindowSize from "../../util/WindowSize";
import { CommentsBox, DisplayBox, Outside, PostBox } from "../common/Box";
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "../../api/article/FindWritingApi";
import ArticleBody from "./ArticleBody";
import Comment from "./Comment";
import CommentWriting from "./CommentWriting";
import { ACCESS_TOKEN } from "../../constants/SessionStorage";
import { withRouter } from "react-router-dom";
import { GROUPED, PRIVATE, PUBLIC } from "../../constants/MapType";
import ChangeContentModal from "./editContent/ChangeContentModal";

const ArticleDetailPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const articleId = props.match.params.articleId;
  const mapType = props.match.params.mapType;

  const [article, setArticle] = useState(null);
  const [hasILiked, setHasILiked] = useState(false);
  const [articleTotalLikes, setArticleTotalLikes] = useState(0);
  const [comments, setComments] = useState([]);
  const [hasILikedListInComment, setHasILikedListInComment] = useState([]);
  const [changeTotalLikesInComment, setChangeTotalLikesListInComment] =
    useState([]);
  const [myId, setMyId] = useState(null);
  const [groupId, setGroupId] = useState(
    props.location.state ? props.location.state.groupId : 0
  );
  const [isChangeContentModalOpened, setIsChangeContentModalOpeneded] =
    useState(false);

  const onCommentLikeClicked = (commentId) => {
    if (hasILikedListInComment.includes(commentId)) {
      setHasILikedListInComment(
        hasILikedListInComment.filter((e) => e !== commentId)
      );
    } else {
      setHasILikedListInComment([...hasILikedListInComment, commentId]);
    }
    if (changeTotalLikesInComment.includes(commentId)) {
      setChangeTotalLikesListInComment(
        changeTotalLikesInComment.filter((e) => e !== commentId)
      );
    } else {
      setChangeTotalLikesListInComment([
        ...changeTotalLikesInComment,
        commentId,
      ]);
    }
  };

  useEffect(() => {
    if (mapType === GROUPED || mapType === PUBLIC || mapType === PRIVATE) {
      findWritingApi(articleId, mapType, props.history).then(
        (articlePromise) => {
          setArticle(articlePromise.articleDetails);
          setArticleTotalLikes(articlePromise.articleDetails.totalLikes);
          setHasILiked(articlePromise.articleLike);
          setComments(articlePromise.comments);
          setHasILikedListInComment(articlePromise.commentLikes);
          setMyId(articlePromise.myMemberId)
        }
      );
    } else {
      alert("주소가 올바르지 않습니다.");
      props.history.push("/");
    }
  }, [articleId, mapType]);

  return (
    <Outside>
      <ChangeContentModal
        isChangeContentModalOpened={isChangeContentModalOpened}
        setIsChangeContentModalOpened={setIsChangeContentModalOpeneded}
        accessToken={accessToken}
        articleId={articleId}
        history={props.history}
      />
      <DisplayBox style={{ height: WindowSize().height - 50, marginTop: 15 }}>
        <PostBox>
          <ArticleMeta
            author={
              article
                ? {
                    nickName: article ? article.writerNickName : "",
                    imageUrl: article ? article.writerImageUrl : "",
                  }
                : {
                    nickName: "",
                    imageUrl: "",
                  }
            }
            createDate={article ? article.createDate : ""}
            markerLat={article ? article.latitude : ""}
            markerLng={article ? article.longitude : ""}
            mapType={mapType}
            groupId={groupId}
          />
          <ArticleBody
            accessToken={accessToken}
            articleId={articleId}
            mapType={mapType}
            article={article}
            articleTotalLikes={articleTotalLikes}
            setArticleTotalLikes={setArticleTotalLikes}
            hasILiked={hasILiked}
            setHasILiked={setHasILiked}
            history={props.history}
            isMyArticle={article ? article.writerId === myId : false}
            setIsChangeContentModalOpened={setIsChangeContentModalOpeneded}
          />
        </PostBox>
        <CommentsBox>
          <CommentWriting
            mapType={mapType}
            articleId={articleId}
            comments={comments}
            setComments={setComments}
            accessToken={accessToken}
            history={props.history}
          />
          {comments
            ? comments.map((comment, idx) => (
                <Comment
                  key={idx}
                  accessToken={accessToken}
                  articleId={articleId}
                  comment={comment}
                  mapType={mapType}
                  commentTotalLikes={comment.totalLikes}
                  isMine={comment.author.id === myId}
                  hasILikedListInComment={hasILikedListInComment}
                  onCommentLikeClicked={onCommentLikeClicked}
                  changeTotalLikesInComment={changeTotalLikesInComment}
                  history={props.history}
                />
              ))
            : "아직 댓글이 없습니다 :)"}
        </CommentsBox>
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(ArticleDetailPage);
