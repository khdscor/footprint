import React from 'react';
import Profile from "./Profile";
import Nickname from "./NickName";
import DeportButton from './Deport';

const Member = ({userId, nickname, userImageUrl, group, accessToken}) => {
  return (
    <div style={{
      display: "flex",
      flexDirection: "row",
      alignItems: "center"
    }}>
      <Profile userImageUrl={userImageUrl}/>
      <Nickname nickname={nickname}/>
      {group.owner ?
      <DeportButton 
        groupId={group.id} 
        userId={userId}
        accessToken={accessToken}/>
        : ""
      }
    </div>
  );
};

export default Member;