import React, { useEffect, useState } from "react";
import PostCard from "./PostCard";
import axios from "axios";
const MainPage = () => {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    async function getPosts() {
      const res = await axios.get("http://localhost:8080/api/posts");

      setPosts(res.data);
    }

    getPosts();
  }, []);
  return (
    <div className="main universal-padding">
      {posts?.length === 0 ? (
        <h1>No posts avalible to read...</h1>
      ) : (
        posts.map((post, key) => (
          <PostCard
            key={key}
            id={post.postId}
            title={post.title}
            createdAt={new Date(post.createdAt).toLocaleString()}
            content={post.content}
          />
        ))
      )}
    </div>
  );
};

export default MainPage;
