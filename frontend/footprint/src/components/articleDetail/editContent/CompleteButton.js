import React from "react";
import {withRouter} from "react-router-dom";
import editArticleContentApi from "../../../api/article/EditArticleContentApi";
import editCommentApi from "../../../api/comment/EditCommentApi";

const CompleteButton = ({
  isChangeContentModalOpened, 
  newContent, 
  accessToken, 
  id, 
  history, 
  isEditArticle
}) => {
  return <>
    {isChangeContentModalOpened && <button
      onClick={() => {
        if (!newContent) {
          alert("수정할 내용을 작성해주세요!");
          return;
        }
        
        isEditArticle
        ? editArticleContentApi({newContent,accessToken,id,history}) 
        : editCommentApi({newContent, id, accessToken, history})

      }}
      style={{
        backgroundColor: "#00000000",
        width: "60%",
        height: "50px",
        margin: "10px",
        borderRadius: "20px",
        fontSize: "18px",
        fontFamily: "'Gowun Dodum', sans-serif",
        color: "#445566"
      }}
    > 수정하기 </button>
    }
  </>;
};

export default withRouter(CompleteButton);