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
          setHasILikedListInComment(articlePromise.commentLikes);
          setMyId(articlePromise.myMemberId)
        }
      );
    } else {
      alert("주소가 올바르지 않습니다.");
      props.history.push("/");
    }
  }, [articleId, mapType]);

//-------------------------------------------------------
const [pins, setPins] = useState([]);
const [page, setPage] = useState(1); //스크롤이 닿았을 때 새롭게 데이터 페이지를 바꿀 state
const [loading, setLoading] = useState(false); //로딩 성공, 실패를 담을 state

const fetchPins = async page => {
 //데이터 삽입
  console.log("하하하핳")
  setLoading(true);
};

useEffect(() => {
  fetchPins(page);
}, [page]);

const loadMore = () => {
  setPage(prev => prev + 1);
}

const pageEnd = useRef();

useEffect(() => {
  if (loading) {
    //로딩되었을 때만 실행
    const observer = new IntersectionObserver(
      entries => {
        if (entries[0].isIntersecting) {
          loadMore();
        }
      },
      { threshold: 1 }
    );
    //옵져버 탐색 시작
    observer.observe(pageEnd.current);
  }
}, [loading]);

// const target = useRef(null);
// const [pageNum, setPageNum] = useState(0);
// const [isLoading, setIsLoading] = useState(false);
//   useEffect(() => {
//     observer.observe(target.current);
//   }, []);
//   useEffect(() => {
//     setIsLoading(false)
//   }, [pageNum]);
//   const options = {
//     threshold: 1.0,
//   };
// console.log(isLoading)
//   const callback = () => {
//     if(isLoading)
//     {
//       console.log("kkkkkkk")
//     setIsLoading(true)
//     }
//     else {
//       setIsLoading(true)
//       console.log("ggggg")
//     }
//   };

//   const observer = new IntersectionObserver(callback, options);

 //
//-------------------------------------------------------


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
            <div ref={pageEnd} style={{backgroundColor: "red", height: "100px"}}></div>
            <div  style={{backgroundColor: "blue", height: "100px"}}></div>
        </CommentsBox>
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(ArticleDetailPage);
