import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const changeMyLikeApi = (accessToken, hasILiked, articleId, mapType, history) => {
  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return;
  }

  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }

  axios.post(BACKEND_ADDRESS + "/articles/" +mapType + "/" + articleId + "/like?hasiliked=" + hasILiked,{}, config)
  .then(response => {
    if (response.status === 201) {
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
      return;
    } else if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return;
    } else alert("이유가 뭔지 모르겠지만 좋아요 실패했음.");
    history.push("/");
  });
};

export default changeMyLikeApi;