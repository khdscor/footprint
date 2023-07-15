import React from 'react';
import styled from "styled-components";
import deleteArticleApi from "../../api/article/DeleteArticleApi";

const DeleteButtonStyle = styled.div`
  color: #777777;
  display: inline-block;
  cursor: pointer;
`;

const DeleteButton = ({articleId, accessToken, history}) => {
  const onDeleteButtonClicked = () => {
    if (window.confirm("정말 삭제하시겠습니까?")) {
      deleteArticleApi({articleId, accessToken, history});
    }
  };
  return (
    <DeleteButtonStyle onClick={onDeleteButtonClicked}>
      삭제
    </DeleteButtonStyle>
  );
};

export default DeleteButton;
