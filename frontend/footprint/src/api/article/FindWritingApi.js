import axios from "axios";
import {PUBLIC} from "../../constants/MapType";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";

const findWritingApi = (articleId, mapType, history, setArticle, setArticleTotalLikes,setHasILiked, 
  setComments, setCommentCursorId, setHasNextPage, setHasILikedListInComment, setMyId) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const config = {
    headers: {
      "Authorization": "Bearer " + accessToken
    }
  };
  if (mapType === PUBLIC) {
    return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/details/" + articleId, config)
    .then((response) => {
        setArticle(response.data.articleDetails);
        setArticleTotalLikes(response.data.articleDetails.totalLikes);
        setHasILiked(response.data.articleLike);
        setComments(response.data.comments);
        if(response.data.comments.length >= 10){
          setCommentCursorId(response.data.comments[9].id);
          setHasNextPage(true);
        }
        setHasILikedListInComment(response.data.commentLikes);
        setMyId(response.data.myMemberId)
      })
    .catch(error => {
      if (error.response.status === 400 || error.response.status === 404) {
        alert(error.response.data.errorMessage);
        history.push("/");
        return;
      } else alert("게시 글 가져오기에 실패했음...");
    });
  }

  // 개인지도 or 그룹지도
  
  return axios.get(BACKEND_ADDRESS + "/articles/" + mapType + "/details/" + articleId, config)
  .then((response) => {
    setArticle(response.data.articleDetails);
    setArticleTotalLikes(response.data.articleDetails.totalLikes);
    setHasILiked(response.data.articleLike);
    setComments(response.data.comments);
    if(response.data.comments.length >= 10){
      setCommentCursorId(response.data.comments[9].id);
      setHasNextPage(true);
    }
    setHasILikedListInComment(response.data.commentLikes);
    setMyId(response.data.myMemberId)
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    } else if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      history.push("/");
      return;
    } else alert("게시 글 가져오기에 실패했음...");
  });
};

export default findWritingApi;