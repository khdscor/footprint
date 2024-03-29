import React from 'react';
import {GROUPED, PRIVATE, PUBLIC} from "../../constants/MapType";
import styled from "styled-components";

const Style = styled.div`
  display: inline-block;
  font-size: 18px;
  border-radius: 20px;
  cursor: pointer;
  position: fixed;
  zIndex: 10;
  top: 0;
  right: ${props => props.rightMargin};
  width: 220px;
  margin: 20px;
  text-align: right;
  padding: 5px 0;
  font-weight: bold;
`;

const MapType = ({mapType, groupId, groupName, rightMargin, history}) => {
  if (mapType === GROUPED && groupId > 0) {
    return <Style rightMargin={rightMargin} onClick={() => history.push("/groups/" + groupId)}>
      {groupName}
    </Style>
  }
  if (mapType === PUBLIC || (mapType === GROUPED && groupId === undefined)) {
    return <Style rightMargin={rightMargin}>전체지도</Style>;
  }
  if (mapType === PRIVATE) {
    return <Style rightMargin={rightMargin}>개인지도</Style>;
  }
};

export default MapType;