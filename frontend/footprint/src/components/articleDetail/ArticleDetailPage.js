import React, { useEffect, useState, useRef } from "react";
import WindowSize from "../../util/WindowSize";
import { CommentsBox, DisplayBox, Outside, PostBox } from "../common/Box";
import ArticleMeta from "./ArticleMeta";
import findWritingApi from "../../api/article/FindWritingApi";
import ArticleBody from "./ArticleBody";
import Comment from "./comment/Comment";
import CommentWriting from "./comment/CommentWriting";
import { ACCESS_TOKEN } from "../../constants/SessionStorage";
import { withRouter } from "react-router-dom";
import { GROUPED, PRIVATE, PUBLIC } from "../../constants/MapType";
import ChangeContentModal from "./editContent/ChangeContentModal";
import findCommentApi from "../../api/comment/FindCommentApi";
import { useInView } from "react-intersection-observer"

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

  const [editCommentId, setEditCommentId] = useState(0);
  const [isChangeContentModalOpened, setIsChangeContentModalOpeneded] =
    useState(false);
  const [isChangeCommentModalOpened, setIsChangeCommentModalOpeneded] =
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
          if(articlePromise.comments.length >= 10){
            setCommentCursorId(articlePromise.comments[9].id);
            setHasNextPage(true);
          }
          setHasILikedListInComment(articlePromise.commentLikes);
          setMyId(articlePromise.myMemberId)
        }
      );
    } else {
      alert("주소가 올바르지 않습니다.");
      props.history.push("/");
    }
  }, [articleId, mapType]);

//---------댓글 무한 스크롤 관련
const [commentCursorId, setCommentCursorId] = useState(-1); // 다음 커서 id
const [hasNextPage, setHasNextPage] = useState(false); // 다음 페이지 유무
const [ref, inView] = useInView() // react-intersection-observer 라이브러리

const updateComments = () => {
  findCommentApi(articleId, commentCursorId).then(
    (commentPromise) => {
      setComments([...comments.concat(commentPromise.comments)]);
      setHasNextPage(commentPromise.hasNextPage);
      setCommentCursorId(commentPromise.cursorId);
    })
};

useEffect(() => {
  if(inView && hasNextPage) updateComments()
},[inView]);
//----------------------------------

  return (
    <Outside>
      {/* 게시글 내용 수정 창 */}
      <ChangeContentModal
        isChangeContentModalOpened={isChangeContentModalOpened}
        setIsChangeContentModalOpened={setIsChangeContentModalOpeneded}
        accessToken={accessToken}
        id={articleId}
        history={props.history}
        isEditArticle={true}
      />
      {/* 댓글 내용 수정 창 */}
      <ChangeContentModal
        isChangeContentModalOpened={isChangeCommentModalOpened}
        setIsChangeContentModalOpened={setIsChangeCommentModalOpeneded}
        accessToken={accessToken}
        id={editCommentId}
        history={props.history}
        isEditArticle={false}
      />
      <DisplayBox style={{ height: WindowSize().height - 50, marginTop: 15 }}>
        <PostBox>
          <ArticleMeta
            author={
              article
                ? {
                    nickName: article.author ? article.author.nickName : "",
                    imageUrl: article.author ? article.author.imageUrl : "",
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
            isMyArticle={article ? article.author.id === myId : false}
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
                  setEditCommentId={setEditCommentId}
                  setIsChangeCommentModalOpeneded={setIsChangeCommentModalOpeneded}
                />
              ))
            : "아직 댓글이 없습니다 :)"}
            {hasNextPage ? 
            <div ref={ref} style={{textAlign: "center"}}>
            <img src="/loading.png" alt="my image" style={{width: "60px", height: "60px"}}/>
            </div> : ""}
        </CommentsBox>
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(ArticleDetailPage);