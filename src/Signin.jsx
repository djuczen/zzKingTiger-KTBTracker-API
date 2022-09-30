import React from "react";
import { useNavigate } from "react-router-dom";
import { useAuthState } from "react-firebase-hooks/auth";

import { auth, signInWithEmail, signInWithGoogle } from "../config/firebase";


function Signin() {

  const [email, setEmail] = React.useState("");
  const [password, setPassword] = React.useState("");
  const [user, loading, error] = useAuthState(auth);
  const navigate = useNavigate();

  React.useEffect(() => {
    if (loading) {
      return;
    }
    if (user) {
      navigate("/");
    }
  }, [user, loading]);

  return (
    <div className="container">
      <div className="row">
        <div className="col-md-5 mx-auto">
          <div id="signin-top">
            <div className="signin-form form">
              <div className="logo mb-3">
                <div className="col-md-12 text-center">
                  <h1>Sign in</h1>
                </div>
              </div>
              <div className="form-floating mb-3">
                <input type="email" name="email" className="form-control" id="signin-email" aria-describedby="email-help" placeholder="Enter Email"
                  value={email} onChange={(e) => setEmail(e.target.value)}/>
                <label htmlFor="signin-email">Email Address</label>
              </div>
              <div className="form-floating mb-3">
                <input type="password" name="password" className="form-control" id="signin-password" aria-describedby="password-help" placeholder="Enter Password"
                  value={password} onChange={(e) => setPassword(e.target.value)}/>
                <label htmlFor="signin-password">Password</label>
              </div>
              <div className="form-group">
                <p className="text-center">By signing up you accept our <a href="javascript:void();">Terms Of Use</a></p>
              </div>
              <div className="col-md-12 text-center ">
                <button className=" btn btn-block btn-primary"
                  onClick={() => signInWithEmail(email, password)}>Login</button>
              </div>
              <div className="col-md-12 ">
                <div className="login-or">
                  <hr className="hr-or"/>
                  <span className="span-or">or</span>
                </div>
              </div>
              <div className="col-md-12 mb-3">
                <p className="text-center">
                  <a href="javascript:void();" className="google btn mybtn">
                    <i className="fa fa-google-plus"></i>
                    Signup using Google
                  </a>
                </p>
              </div>
              <div className="form-group">
                <p className="text-center">Don't have account? <a href="#" id="signup">Sign up here</a></p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Signin;
