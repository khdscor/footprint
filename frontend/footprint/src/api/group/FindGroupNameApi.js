import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findGroupNameApi = ({ groupId, accessToken, history, setGroupName}) => {
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
  return axios.get(BACKEND_ADDRESS + "/groups/" + groupId + "/name", config)
  .then(response => {
    if (response.status === 200) {
      setGroupName(response.data.name);
    }
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
    } else alert("이유가 뭔지 모르겠지만 내 그룹을 불러오는데 실패했음...");
  });
};

export default findGroupNameApi;