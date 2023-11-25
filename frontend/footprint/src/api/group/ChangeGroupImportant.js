import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const changeGroupImportant = ({groupId, accessToken, history}) => {
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
  axios.put(BACKEND_ADDRESS + "/groups/" + groupId + "/important", null, config)
  .then(response => {})
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
      history.push("/login");
    } else if(error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return;
    } else {
      alert("중요 그룹 표시 토글 실패");
    }
    return;
  });
};

export default changeGroupImportant;
