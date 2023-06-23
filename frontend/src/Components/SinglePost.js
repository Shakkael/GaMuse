import React, { useEffect, useState } from "react";
import CommentCard from "./CommentCard";
import { useParams } from "react-router-dom";
import axios from "axios";
import CreateComment from "./CreateComment";

const SinglePost = () => {
  const { postId } = useParams();

  const [post, setPost] = useState({
    id: "",
    title: "",
    content: "",
    createdAt: "",
  });

  const [comments, setComments] = useState([]);

  useEffect(() => {
    const getSinglePost = async () => {
      const res = await axios.get(`http://localhost:8080/api/posts/${postId}`);
      setPost(res.data);
    };
    const getPostComments = async () => {
      const res = await axios.get(
        `http://localhost:8080/api/posts/${postId}/comments`
      );
      setComments(res.data);
    };
    getSinglePost();
    getPostComments();
  }, []);

  return (
    <div className="singlepost universal-padding">
      <div className="post">
        <h1 className="post__title">{post.title}</h1>
        <p className="post__createdAt">
          {new Date(post.createdAt).toLocaleString()}
        </p>
        <p className="post__content">{post.content}</p>
        <p className="post__commentnumber">23 Comments</p>
      </div>
      <div className="comments">
        <h3>
          {comments.length} {comments.length === 1 ? "Comment" : "Comments"}
        </h3>
        {JSON.parse(localStorage.getItem("isLogged")) ? (
          <CreateComment postId={postId} />
        ) : (
          "You have to be logged in to add comment..."
        )}
      </div>
    </div>
  );
};

export default SinglePost;
