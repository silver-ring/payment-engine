import React, {Fragment} from 'react';
import {Redirect, Route, Switch} from "react-router";
import Login from "../view/Login";
import AppContainer from "../container/AppContainer";
import {getAuth} from "../store/Auth";
import UserRegistrationConfirmation from "../view/UserRegistrationConfirmation";
import ResetUserPasswordConfirmation
  from "../view/ResetUserPasswordConfirmation";
import ChangePasswordConfirmation from "../view/ChangePasswordConfirmation";

const IndexRouter = () => {

  const auth = getAuth();

  const userRegistrationConfirmationRouter = (
      <Route path="/UserRegistrationConfirmation/:userRegistrationToken"
             component={UserRegistrationConfirmation}/>);

  const resetUserPasswordRouter = (
      <Route path="/ResetUserPasswordConfirmation/:resetUserPasswordToken"
             component={ResetUserPasswordConfirmation}/>);

  const changePasswordConfirmationRouter = (
      <Route path="/ChangePasswordConfirmation/:changePasswordToken"
             component={ChangePasswordConfirmation}/>);

  const loginRouter = (<Route path="/login" component={Login}/>);

  let appRouter = (<Fragment/>);

  if (auth != null) {
    appRouter = <Route path="/" component={AppContainer}/>
  } else {
    appRouter = <Redirect to="/login"/>
  }

  return (
      <Switch>
        {userRegistrationConfirmationRouter}
        {resetUserPasswordRouter}
        {changePasswordConfirmationRouter}
        {loginRouter}
        {appRouter}
      </Switch>
  )
};

export default IndexRouter;
