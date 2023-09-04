import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findCommentApi = (articleId, commentCursorId) => {
    return axios.get(BACKEND_ADDRESS + "/comments/read/" + articleId + "/" + commentCursorId)
      .then(response => response.data)
        .catch(error => {
          if (error.response.status === 500) {
            alert("알 수 없는 오류가 발생하였습니다. 관리자에게 문의해 주십시오.")
            return Promise.reject();
          } 
        });

};

export default findCommentApi;