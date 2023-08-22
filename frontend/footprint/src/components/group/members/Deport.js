import React from 'react';
import deportMemberApi from '../../../api/group/DeportMemberApi';
import styled from "styled-components";

const DeportStyle = styled.div`
display: inline-block;
margin-left: auto;
width: 50px;
height: 25px;
color: #777777;
cursor: pointer;
`
// const ProfileStyle = styled.div`

// `;

// const EditGroupNameButton = styled.div`
//   font-size: 12px;
//   
//   cursor: pointer;
// `;
const DeportButton = ({groupId, userId, accessToken}) => {
  return (
    <DeportStyle onClick={()=>{
      if (window.confirm("정말 추방하시겠습니까?")) {
        deportMemberApi({groupId, userId, accessToken})
      }}}>
      추방
    </DeportStyle>
  );
};

export default DeportButton;