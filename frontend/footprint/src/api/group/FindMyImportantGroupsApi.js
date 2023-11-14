import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findMyImportantGroupsApi = ({accessToken, setImportantGroups}) => {
  if (!accessToken) {
    return;
  }
  const config = {
    headers: {
      Authorization: "Bearer " + accessToken
    }
  }
  return axios.get(BACKEND_ADDRESS + "/groups/mine/important", config)
  .then(response => {
    if (response.status === 200) {
      setImportantGroups(response.data);
    }
  })
  .catch(() => {return;});
};

export default findMyImportantGroupsApi;
