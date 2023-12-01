import React from 'react';
import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const withdrawUserApi = ({accessToken, history}) => {
  if (!accessToken) {
    alert("로그인이 필요한 서비스입니다.")
    history.push('/login');
    return;
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  };
    axios.delete(BACKEND_ADDRESS + "/member/me", config)
    .then(response => {
      if (response.status === 204) {
        alert("회원 탈퇴가 완료되었습니다");
        history.push("/");
      }
    })
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
        history.push("/login");
      } else if(error.response.status === 400 || error.response.status === 404) {
        alert(error.response.data.errorMessage);
        return;
      } else {
        alert("회원 탈퇴 실패ㅜ");
      }
      return;
    });
};

export default withdrawUserApi;