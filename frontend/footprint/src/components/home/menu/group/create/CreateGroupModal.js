import React, {useState} from 'react';
import Modal from "react-modal";
import CancelButton from "./CancelButton";
import GroupNameInput from "./GroupNameInput";
import CompleteButton from "./CompleteButton";

const CreateGroupModal = ({isCreateGroupModalOpen, setIsCreateGroupModalOpen, setIsGroupOpened}) => {
  const [groupName, setGroupName] = useState("");

  return (
    <Modal
      isOpen={isCreateGroupModalOpen}        //모달의 오픈유무를 bool값으로 정한다.
      shouldCloseOnOverlayClick={false}     //close버튼을 눌러야만 모달이 종료
      style={{
        overlay: {
          backgroundColor: '#00112255'
        },
        content: {
          maxWidth: '500px',
          margin: 'auto',
          backgroundColor: 'white',
          maxHeight: '300px',
          textAlign: 'center'
        }
      }}
      contentLabel="create group"       //모달의 라벨
    >
      <CancelButton
        setIsCreateGroupModalOpen={setIsCreateGroupModalOpen}
        setGroupName={setGroupName}
        setIsGroupOpened={setIsGroupOpened}
      />
      <GroupNameInput
          maxLength='12'
        value={groupName}
        onChange={e => setGroupName(e.target.value)}
        placeholder={"새 그룹 이름"}
      />
      <CompleteButton
        isCreateGroupModalOpen={setIsCreateGroupModalOpen}
        groupName={groupName}
      />
    </Modal>
  );
};

export default CreateGroupModal;