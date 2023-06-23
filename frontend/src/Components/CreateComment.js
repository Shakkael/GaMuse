import axios from "axios";
import React, { useContext, useState } from "react";

import { GlobalContext } from "../Context/GlobalContext";

const CreateComment = ({ postId }) => {
  const { globalContext, setGlobalContext } = useContext(GlobalContext);

  const [comment, setComment] = useState("");
  const onSubmit = async (e) => {
    e.preventDefault();
    const res = await axios.post(`http://localhost:8080/api/comment`, {
      content: comment,
      postId,
      userId: globalContext.userId,
    });
    console.log(res);
  };
  return (
    <form className="createcomment" onSubmit={(e) => onSubmit(e)}>
      <input
        className="modal__input"
        onChange={(e) => setComment(e.target.value)}
        type="text"
        name="comment"
        placeholder="Add new comment..."
        value={comment}
      />
      <button className="modal__btn">Add comment</button>
    </form>
  );
};

export default CreateComment;
