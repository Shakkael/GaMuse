import React from "react";
import { Link } from "react-router-dom";

const PostCard = ({ id, title, createdAt, content }) => {
  return (
    <Link to={`/post/${id}`}>
      <div className="postcard">
        <h1 className="postcard__title">{title}</h1>
        <p className="postcard__createdAt">{createdAt}</p>
        <p className="postcard__content">{content}</p>
      </div>
    </Link>
  );
};

export default PostCard;
