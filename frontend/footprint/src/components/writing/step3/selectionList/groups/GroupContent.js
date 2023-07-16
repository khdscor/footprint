import React from 'react';
import styled from "styled-components";

const GroupContentStyle = styled.div`
  height: 50px;
  font-size: 110%;
  margin-bottom: 6px;
  margin-left: 20px;
`;

const GroupContent = (props) => {
  return (
      <GroupContentStyle onClick={props.onClick}>
        {props.children}
      </GroupContentStyle>
  );
};

export default GroupContent;