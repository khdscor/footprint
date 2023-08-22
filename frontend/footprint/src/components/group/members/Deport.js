import React from 'react';
import deportMemberApi from '../../../api/group/DeportMemberApi';


// const ProfileStyle = styled.div`
//   display: inline-block;
//   margin: 10px 18px;
//   width: 50px;
//   height: 50px;
//   background-image: url("${props => props.userImageUrl}");
//   background-size: contain;
//   border-radius: 100px;
//   border: 2px solid black;
// `;

// const EditGroupNameButton = styled.div`
//   font-size: 12px;
//   color: #777777;
//   cursor: pointer;
// `;
const DeportButton = ({groupId, userId, accessToken}) => {
  return (
    <div onClick={()=>{deportMemberApi({groupId, userId, accessToken})}}>
      추방
    </div>
  );
};

export default DeportButton;