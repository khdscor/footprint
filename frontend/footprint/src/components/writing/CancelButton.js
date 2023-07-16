import React from 'react';
import styled from "styled-components";
import {withRouter} from "react-router-dom";

const StyledCancelButton = styled.div`
  display: inline-block;
  position: absolute;
  top: -15px;
  right: -5px;
  width: 30px;
  height: 36px;
  color: #446677;
  font-size: 36px;
  font-weight: bold;
  cursor: pointer;
`;

const CancelButton = ({history}) => {
  return (<div style={{ position: "relative", width: "100%", height: "36px"}}>
      <StyledCancelButton onClick={() => {
        if (window.confirm("글작성을 취소하겠습니까? 작성중이신 글은 저장되지 않습니다.")) {
          history.push("/");
        }
      }}>x</StyledCancelButton>
  </div>
  );
};

export default withRouter(CancelButton);