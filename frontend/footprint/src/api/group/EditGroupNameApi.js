import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const editGroupNameApi = ({ groupId, newGroupName, accessToken, history }) => {
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
  const body = {
    newName: newGroupName
  };
  return axios.put(BACKEND_ADDRESS + "/groups/" + groupId, body, config)
    .catch(error => {
      if (error.response.status === 401 || error.response.status === 403) {
        alert(error.response.data.errorMessage);
        history.push("/login");
      } else if(error.response.status === 400 || error.response.status === 404) {
        alert(error.response.data.errorMessage);
        return;
      } else {
        alert("그룹이름 변경 실패");
      }
      return;
    });
};

export default editGroupNameApi;
