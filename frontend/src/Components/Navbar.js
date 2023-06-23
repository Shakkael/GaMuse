import React, { useContext, useEffect, useState } from "react";
import { GlobalContext } from "../Context/GlobalContext";
import { Link } from "react-router-dom";
import Form from "./Form";
const Navbar = () => {
  const { globalContext, setGlobalContext } = useContext(GlobalContext);

  return (
    <div className="navbar universal-padding">
      <h3>
        <Link to={"/"}>gaMuse</Link>
      </h3>
      {globalContext.isLogged ? (
        globalContext.admin ? (
          <div className="modal__wrapper">
            <a>{globalContext.username}</a>
            <a
              onClick={() => {
                localStorage.setItem("isLogged", false);
                localStorage.setItem("admin", false);
                localStorage.setItem("username", null);
                localStorage.setItem("userId", null);
                setGlobalContext({
                  ...globalContext,
                  admin: false,
                  isLogged: false,
                  username: "",
                  userId: "",
                });
              }}
            >
              Logout
            </a>
          </div>
        ) : (
          <div className="modal__wrapper">
            <a>{globalContext?.username}</a>
            <a
              onClick={() => {
                localStorage.setItem("isLogged", false);
                setGlobalContext({
                  ...globalContext,
                  isLogged: false,
                  username: "",
                  userId: "",
                });
              }}
            >
              Logout
            </a>
          </div>
        )
      ) : (
        <Form />
      )}
    </div>
  );
};

export default Navbar;
