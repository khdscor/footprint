import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findUserApi = (accessToken, setUser, setMyGroups, setMyArticles) => {
   const config = {
     headers: {
       "Authorization": "Bearer " + accessToken
     }
   };
  return axios.get(BACKEND_ADDRESS + "/member/me", config)
  .then(response => {
    if (response.status === 200) {
      setUser(response.data.myInfo)
      setMyGroups(response.data.myGroups)
      setMyArticles(response.data.myArticles)
    }
  })
  .catch(error => {
    if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return;
    } else alert("이유가 뭔지 모르겠지만 내 정보를 불러오는데 실패했음...");
  });
};

export default findUserApi;