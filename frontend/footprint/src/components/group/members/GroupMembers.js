import React from 'react';
import Member from "./Member";

const GroupMembers = ({members, group, accessToken}) => {
  return (
    <>
      {
        members.map((members, index) => <Member
          userId={members.id}
          nickname={members.nickName}
          userImageUrl={members.imageUrl}
          group={group}
          accessToken={accessToken}
          index={index}
        />)
      }
    </>
  );
};

export default GroupMembers;
