import React from 'react';
import Member from "./Member";

const GroupMembers = ({members, group, accessToken}) => {
  return (
    <>
      {
        members.map(member => <Member
          userId={member.id}
          nickname={member.nickName}
          userImageUrl={member.imageUrl}
          group={group}
          accessToken={accessToken}
        />)
      }
    </>
  );
};

export default GroupMembers;
