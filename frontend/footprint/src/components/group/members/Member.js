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
      {group.id}
      <Profile userImageUrl={userImageUrl}/>
      <Nickname nickname={nickname}/>
      <DeportButton 
        groupId={group.id} 
        userId={userId}
        accessToken={accessToken}/>
    </div>
  );
};

export default Member;