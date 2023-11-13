import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const deportMemberApi = ({groupId, userId, accessToken}) => {
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  };
  axios.delete(BACKEND_ADDRESS + "/groups/" + groupId + "/" + userId, config)
  .then(response => {
    if (response.status === 204) {
      alert("그룹원을 추방하였습니다 :)");
      window.location.reload();
    }
  })
  .catch(error => {
    if (error.response.status === 401 || error.response.status === 403) {
      alert("로그인이 만료되었습니다. 다시 로그인해주세요.");
    } else if (error.response.status === 400 || error.response.status === 404) {
      alert(error.response.data.errorMessage);
      return;
    } else {
      alert("그룹 추방 실패..원인을 모르겠네요");
    }
    return;
  });
};
export default deportMemberApi;
