import React from "react";
import axios from "axios";
import { BACKEND_ADDRESS } from "../../constants/ADDRESS";
import { ACCESS_TOKEN } from "../../constants/SessionStorage";

const emailLoginApi = (email, password, history) => {
  const body = {
    email: email,
    password: password,
  };

  axios
    .post(BACKEND_ADDRESS + "/auth/login", body)
    .then((response) => {
      if (response.status === 200) {
        sessionStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
        history.push("/");
      }
    })
    .catch((error) => {
      alert(error.response.data.errorMessage);
      return Promise.reject();
    });
};

export default emailLoginApi;
