import React from "react";
import { redirect } from "react-router-dom";
import { useAuthState } from "react-firebase-hooks/auth";
import { Dropdown } from 'bootstrap';

import { auth, logout } from "../config/firebase";


function Header() {
  const [user, loading, error] = useAuthState(auth);
  const dropdownRef = React.useRef();
  console.log(user, loading, error);

  React.useEffect(() => {
    new Dropdown(dropdownRef.current, {});
  });

  return (
    <header>
    <nav className="navbar navbar-expand-lg bg-primary">
      <div className="container-fluid">
        <a className="navbar-brand text-light" href="/">Navbar</a>
        <button className="navbar-toggler text-light" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-content"
          ref={dropdownRef} aria-controls="navbar-content" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbar-content">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link active text-light" aria-current="page" href="/">Home</a>
            </li>
            <li className="nav-item">
              <a className="nav-link text-light" href="/">Link</a>
            </li>
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle text-light" href="/" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Dropdown
              </a>
              <ul className="dropdown-menu">
                <li><a className="dropdown-item" href="/">Action</a></li>
                <li><a className="dropdown-item" href="/">Another action</a></li>
                <li><hr className="dropdown-divider"/></li>
                <li><a className="dropdown-item" href="/">Something else here</a></li>
              </ul>
            </li>
            <li className="nav-item">
              <a className="nav-link disabled" href="/">Disabled</a>
            </li>
          </ul>
          {user !== null &&
            <div className="dropdown mx-2">
              <button className="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false"
                ref={dropdownRef}>
                {user?.displayName || user?.email || "?"}
              </button>
              <ul className="dropdown-menu">
                <li><a className="dropdown-item" href="javascript:void();" onClick={() => handleLogout()}>Logout</a></li>
              </ul>
            </div>
          }
          <form className="d-flex" role="search">
            <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search"/>
            <button className="btn btn-outline-light" type="submit">Search</button>
          </form>
        </div>
      </div>
    </nav>
    </header>
  );
}

function handleLogout() {
  logout();
  redirect("/signin");
}

export default Header;
