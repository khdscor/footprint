import axios from "axios";
import {BACKEND_ADDRESS} from "../../constants/ADDRESS";

const findUserImageUrlApi = (accessToken, setUserImageUrl) => {
   const config = {
     headers: {
       "Authorization": "Bearer " + accessToken
     }
   };
  return axios.get(BACKEND_ADDRESS + "/member/me/image", config)
  .then(response => {setUserImageUrl(response.data)});
};

export default findUserImageUrlApi;