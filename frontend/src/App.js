import { BrowserRouter, Routes, Route, json } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Mainpage from "./Components/Mainpage";
import SinglePost from "./Components/SinglePost";
import CreatePost from "./Components/CreatePost";
import { useEffect, useContext, useState } from "react";
import { GlobalContext } from "./Context/GlobalContext";
import { obj, posts } from "./DummyData/dummy";
import axios from "axios";

function App() {
  const [globalContext, setGlobalContext] = useState({
    isLogged: JSON.parse(localStorage.getItem("isLogged")),
    admin: JSON.parse(localStorage.getItem("admin")),
    userId: JSON.parse(localStorage.getItem("userId")),
    username: null,
  });

  useEffect(() => {
    let adminExits = localStorage.getItem("admin") === null;
    if (adminExits) {
      localStorage.setItem("admin", false);
      setGlobalContext({ ...globalContext, admin: false });
    } else {
      setGlobalContext({
        ...globalContext,
        admin: localStorage.getItem("admin"),
      });
    }

    let isLoggedExits = localStorage.getItem("isLogged") === null;
    if (isLoggedExits) {
      localStorage.setItem("isLogged", false);
      setGlobalContext({ ...globalContext, isLogged: false });
    } else {
      setGlobalContext({
        ...globalContext,
        isLogged: localStorage.getItem("isLogged"),
      });
    }

    let userIdExist = localStorage.getItem("userId") === null;
    if (userIdExist) {
      localStorage.setItem("userId", null);
      setGlobalContext({ ...globalContext, userId: null });
    } else {
      setGlobalContext({
        ...globalContext,
        userId: localStorage.getItem("userId"),
      });
    }

    //Username
    let usernameExist = localStorage.getItem("username") === null;
    if (usernameExist) {
      localStorage.setItem("username", null);
      setGlobalContext({ ...globalContext, username: null });
    } else {
      setGlobalContext({
        ...globalContext,
        username: localStorage.getItem("username"),
      });
    }
    //auto-create-users
  }, []);
  return (
    <GlobalContext.Provider value={{ globalContext, setGlobalContext }}>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route element={<Mainpage />} path="/" />
          <Route element={<SinglePost />} path="/post/:postId" />
        </Routes>
        <CreatePost />
      </BrowserRouter>
    </GlobalContext.Provider>
  );
}

export default App;
