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

const CancelButton = (props) => {
  return (<div style={{ position: "relative", width: "100%", height: "36px"}}>
      <StyledCancelButton onClick={() => {
        props.history.push({
          pathname:"/",
          state: {lat:props.markerLat, lng:props.markerLng, mapType: props.mapType, groupId: props.groupId}
        });
      }}>x</StyledCancelButton>
  </div>
  );
};

export default withRouter(CancelButton);
