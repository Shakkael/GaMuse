import React, { useContext, useRef, useState } from "react";
import axios from "axios";
import { adminCredentials } from "../Helpers/admin";
import { GlobalContext } from "../Context/GlobalContext";

const Form = () => {
  const { globalContext, setGlobalContext } = useContext(GlobalContext);
  const [isLogin, setIsLogin] = useState(true);
  const [message, setMessage] = useState({ message: "", success: null });
  const [data, setData] = useState({
    email: "",
    password: "",
    username: "",
  });
  const ref = useRef(null);

  const loginForm = (
    <form className="column" onSubmit={(e) => onSubmit(e)}>
      <input
        onChange={(e) => setData({ ...data, [e.target.name]: e.target.value })}
        className="modal__input"
        type="email"
        name="email"
        placeholder="Email..."
        value={data.email}
      />
      <input
        onChange={(e) => setData({ ...data, [e.target.name]: e.target.value })}
        className="modal__input"
        type="password"
        name="password"
        placeholder="Password"
        value={data.password}
      />
      <div>{message.message}</div>
      <button className="modal__btn" type="submit">
        Login
      </button>
    </form>
  );

  const registrationForm = (
    <form className="column" onSubmit={(e) => onSubmit(e)}>
      <input
        onChange={(e) => setData({ ...data, [e.target.name]: e.target.value })}
        className="modal__input"
        type="username"
        name="username"
        placeholder="Username"
        value={data.username}
      />
      <input
        onChange={(e) => setData({ ...data, [e.target.name]: e.target.value })}
        className="modal__input"
        type="email"
        name="email"
        placeholder="Email..."
        value={data.email}
      />
      <input
        onChange={(e) => setData({ ...data, [e.target.name]: e.target.value })}
        className="modal__input"
        type="password"
        name="password"
        placeholder="Password"
        value={data.password}
      />
      <div>{message.message}</div>
      <button className="modal__btn" type="submit">
        Register
      </button>
    </form>
  );
  const onSubmit = async (e) => {
    e.preventDefault();
    let obj = {};
    if (isLogin) {
      obj = {
        email: data.email,
        password: data.password,
      };
    } else {
      obj = {
        username: data.username,
        email: data.email,
        password: data.password,
      };
    }

    if (
      obj.email !== adminCredentials.email &&
      obj.password !== adminCredentials.password
    ) {
      if (isLogin) {
        var res = await axios.post(
          "http://localhost:8080/api/users/login",
          obj
        );
        console.log(obj);
        if (res.data.success === false) {
          setMessage(res.data);
        } else {
          setMessage("Logged In!");
          setTimeout(() => setMessage(""), 1000);
          ref.current.close();
          localStorage.setItem("admin", false);
          localStorage.setItem("isLogged", true);
          localStorage.setItem("userId", res.data.userId);
          localStorage.setItem("username", res.data.username);
          setGlobalContext({
            isLogged: true,
            admin: false,
            userId: res.data.userId,
            username: res.data.username,
          });
        }
      } else {
        var res = await axios.post(
          "http://localhost:8080/api/users/register",
          obj
        );
        if (res.data.success === true) {
          setMessage(res.data.message);
          setTimeout(() => setMessage(""), 1000);
          ref.current.close();
        } else {
          setMessage(res.data);
        }
      }
    } else if (
      obj.email === adminCredentials.email &&
      obj.password === adminCredentials.password
    ) {
      localStorage.setItem("admin", true);
      localStorage.setItem("isLogged", true);
      setGlobalContext({
        ...globalContext,
        admin: true,
        isLogged: true,
        username: "ADMIN",
      });
      setMessage("Logged as Admin!");
      setTimeout(() => setMessage(""), 1000);
      ref.current.close();
      setData({
        email: "",
        password: "",
        username: "",
      });
    }
  };

  return (
    <div>
      <dialog className="modal" ref={ref}>
        <div className="modal__header">
          <h1>{isLogin ? "Login" : "Register"}</h1>
          <div
            className="modal__close"
            onClick={() => {
              ref.current.close();
              setData({
                email: "",
                password: "",
                username: "",
              });
            }}
          >
            <h1>&times;</h1>
          </div>
        </div>
        {isLogin ? loginForm : registrationForm}
      </dialog>
      <div className="modal__wrapper">
        <a
          onClick={() => {
            setIsLogin(true);
            ref.current.showModal();
          }}
        >
          Login
        </a>

        <a
          onClick={() => {
            setIsLogin(false);
            ref.current.showModal();
          }}
        >
          Register
        </a>
      </div>
    </div>
  );
};

export default Form;
