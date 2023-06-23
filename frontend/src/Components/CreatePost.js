import axios from "axios";
import React, { useRef, useState } from "react";

const CreatePost = () => {
  const ref = useRef(null);
  const [newPost, setNewPost] = useState({
    title: "",
    content: "",
  });

  const onSubmit = async () => {
    axios.post("http://localhost:8080/api/posts", {
      title: newPost.title,
      content: newPost.content,
    });
  };

  return (
    <>
      <dialog className="modal" ref={ref}>
        <div className="column">
          <div className="modal__header">
            <h1>Create Post</h1>
            <div className="modal__close" onClick={() => ref.current.close()}>
              <h1>&times;</h1>
            </div>
          </div>

          <form onSubmit={() => onSubmit()}>
            <input
              className="modal__input"
              type="text"
              placeholder="Title"
              name="title"
              onChange={(e) =>
                setNewPost({ ...newPost, [e.target.name]: e.target.value })
              }
            />
            <textarea
              className="modal__textarea"
              type="text"
              placeholder="Content"
              name="content"
              onChange={(e) =>
                setNewPost({ ...newPost, [e.target.name]: e.target.value })
              }
            ></textarea>
            <button type="submit" className="modal__btn">
              Add post
            </button>
          </form>
        </div>
      </dialog>
      {JSON.parse(localStorage.getItem("admin")) ? (
        <div className="plus">
          <button onClick={() => ref.current.showModal()}>Create Post</button>
        </div>
      ) : (
        ""
      )}
    </>
  );
};

export default CreatePost;
