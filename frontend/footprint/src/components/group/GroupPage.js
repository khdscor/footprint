import React, {useEffect, useState} from 'react';
import WindowSize from "../../util/WindowSize";
import {DisplayBox, Outside} from "../common/Box";
import {ACCESS_TOKEN} from "../../constants/SessionStorage";
import findGroupApi from "../../api/group/FindGroupApi";
import {withRouter} from "react-router-dom";
import GroupHead from "./GroupHead";
import CancelButton from "./CancelButton";
import InvitationCode from "./InvitationCode";
import GroupMembers from "./members/GroupMembers";
import EditNameModal from "./editNameModal/EditNameModal";
import WithdrawButton from "./WithdrawButton";

const GroupPage = (props) => {
  const accessToken = sessionStorage.getItem(ACCESS_TOKEN);
  const groupId = props.match.params.groupId;

  const [group, setGroup] = useState({
    id: groupId,
    name: "",
    invitationCode: "",
    important: false,
    createDate: null
  });
  const [groupMembers, setGroupMembers] = useState([]);

  const [isEditNameModalOpen, setIsEditNameModalOpen] = useState(false);

  useEffect(() => {
    findGroupApi({
      groupId: groupId,
      accessToken: accessToken,
      history: props.history
    }).then(groupPromise => {
      setGroup(groupPromise.groupInfo)
      setGroupMembers(groupPromise.memberDetails)
    });
  }, [groupId, accessToken]);

  return (
    <Outside>
      <DisplayBox style={{height: WindowSize().height - 50, marginTop: 15}}>
        <CancelButton/>
        <GroupHead
          setIsEditNameModalOpen={setIsEditNameModalOpen}
          group={group}
          setGroup={setGroup}
          accessToken={accessToken}
          history={props.history}
        />
        <InvitationCode invitationCode={group.invitationCode}/>
        <hr style={{margin: "20px 0 8px 0"}}/>
        <GroupMembers 
          members={groupMembers}
          group={group}
          accessToken={accessToken}
        />
        <EditNameModal
          group={group}
          setGroup={setGroup}
          accessToken={accessToken}
          isEditNameModalOpen={isEditNameModalOpen}
          setIsEditNameModalOpen={setIsEditNameModalOpen}
        />
        <WithdrawButton
          groupId={groupId}
          accessToken={accessToken}
          history={props.history}
        />
      </DisplayBox>
    </Outside>
  );
};

export default withRouter(GroupPage);