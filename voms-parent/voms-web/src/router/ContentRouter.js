import React from 'react';
import {Redirect, Route, Switch, withRouter} from "react-router";
import ChangePassword from "../view/ChangePassword";
import {getAuthorities} from "../store/Auth";
import MsisdnProvider
  from "../view/VoucherConfig/MsisdnProvider/MsisdnProvider";
import OcsAccount from "../view/VoucherConfig/OcsAccount/OcsAccount";
import VoucherProvider
  from "../view/VoucherConfig/VoucherProvider/VoucherProvider";
import ConsumableVoucherType
  from "../view/VoucherConfig/ConsumableVoucherType/ConsumableVoucherType";
import Promotion from "../view/VoucherConfig/Promotion/Promotion";
import Taxation from "../view/VoucherConfig/Taxation/Taxation";
import RechargeValue from "../view/VoucherConfig/RechargeValue/RechargeValue";
import VoucherType from "../view/VoucherConfig/VoucherType/VoucherType";
import VoucherCreationBatchOrder
  from "../view/VoucherProductionOrder/VoucherCreationBatchOrder/VoucherCreationBatchOrder";
import VoucherPrinter
  from "../view/VoucherConfig/VoucherPrinter/VoucherPrinter";
import VoucherHistory
  from "../view/SystemHistory/VoucherHistory/VoucherHistory";
import ActiveVoucherAdmin
  from "../view/VoucherAdmin/ActiveVoucherAdmin/ActiveVoucherAdmin";
import PendingUsageVoucherAdmin
  from "../view/VoucherAdmin/PendingUsageVoucherAdmin/PendingUsageVoucherAdmin";
import BlockedVoucherAdmin
  from "../view/VoucherAdmin/BlockedVoucherAdmin/BlockedVoucherAdmin";
import UserActivity from "../view/UserManager/UserActivity/UserActivity";
import VoucherPolicySchedule
  from "../view/VoucherPolicy/VoucherPolicySchedule/VoucherPolicySchedule";
import CreatedVoucherAdmin
  from "../view/VoucherAdmin/CreatedVoucherAdmin/CreatedVoucherAdmin";
import BatchOrderHistory
  from "../view/SystemHistory/BatchOrderHistory/BatchOrderHistory";
import ProductionFile
  from "../view/VoucherProductionOrder/ProductionFile/ProductionFile";
import BlockedVoucherModificationOrder
  from "../view/VoucherModificationOrder/BlockedVoucherModificationOrder";
import CreatedVoucherModificationOrder
  from "../view/VoucherModificationOrder/CreatedVoucherModificationOrder";
import CreatedVoucherTransitionOrder
  from "../view/VoucherTransitionOrder/CreatedVoucherTransitionOrder";
import ActiveVoucherTransitionOrder
  from "../view/VoucherTransitionOrder/ActiveVoucherTransitionOrder";
import BlockedVoucherTransitionOrder
  from "../view/VoucherTransitionOrder/BlockedVoucherTransitionOrder";
import PendingUsageVoucherTransitionOrder
  from "../view/VoucherTransitionOrder/PendingUsageVoucherTransitionOrder";
import VoucherPolicyHistory
  from "../view/SystemHistory/VoucherPolicyHistory/VoucherPolicyHistory";
import BatchOrderConfigParameter
  from "../view/ConfigParameter/BatchOrderConfigParameter/BatchOrderConfigParameter";
import FileManagerConfigParameter
  from "../view/ConfigParameter/FileManagerConfigParameter/FileManagerConfigParameter";
import VoucherPolicyConfigParameter
  from "../view/ConfigParameter/VoucherPolicyConfigParameter/VoucherPolicyConfigParameter";
import UserRegistration
  from "../view/UserManager/UserRegistration/UserRegistration";
import UserRole from "../view/UserManager/UserRole/UserRole";
import ResetUserPassword
  from "../view/UserManager/ResetUserPassword/ResetUserPassword";
import UpdateUserRoles
  from "../view/UserManager/UpdateUserRoles/UpdateUserRoles";
import UserProfile from "../view/UserProfile";
import DeleteUserAccount
  from "../view/UserManager/DeleteUserAccount/DeleteUserAccount";
import UserAccount from "../view/UserManager/UserAccount/UserAccount";
import ChangeUserAccountStatus
  from "../view/UserManager/ChangeUserStatus/ChangeUserAccountStatus";
import UserManagementConfigParameter
  from "../view/ConfigParameter/UserManagementConfigParameter/UserManagementConfigParameter";
import RechargeHistory
  from "../view/SystemHistory/RechargeHistory/RechargeHistory";
import UserNotificationEmailHistory
  from "../view/SystemHistory/UserNotificationEmailHistory/UserNotificationEmailHistory";
import RemoteFileTransferHistory
  from "../view/SystemHistory/RemoteFileTransferHistory/RemoteFileTransferHistory";
import EmailConfigParameter
  from "../view/ConfigParameter/EmailConfigParameter/EmailConfigParameter";
import Dashboard from "../view/Dashboard/Dashboard";
import UserEmailDomainMigration
  from "../view/UserManager/UserEmailDomainMigration/UserEmailDomainMigration";
import UserEmailDomainMigrationHistory
  from "../view/SystemHistory/UserEmailDomainMigrationHistory/UserEmailDomainMigrationHistory";

const ContentRouter = (props) => {

  const authorities = getAuthorities();

  if (authorities == null) {
    props.history.push('/login');
  }

  const userManagerRouters = [];
  const configParameterRouters = [];
  const voucherConfigRouters = [];
  const voucherAdminRouters = [];
  const voucherPolicyRouters = [];
  const voucherProductionRouters = [];
  const voucherTransitionRouters = [];
  const voucherModificationRouters = [];
  const systemHistoryRouters = [];
  const logoutRedirect = [];
  const changePasswordRouter = [];
  const userProfileRouter = [];
  const dashboardRouters = [];
  const rootRouters = [];

  logoutRedirect.push(<Redirect key={"Logout"} from="/Logout"
                                to="/Login"/>);

  changePasswordRouter.push(<Route key={"ChangePassword"}
                                   path="/ChangePassword"
                                   component={ChangePassword}/>);

  userProfileRouter.push(<Route key={"UserProfile"}
                                path="/UserProfile"
                                component={UserProfile}/>);

  if (authorities.includes("UserActivity")) {
    userManagerRouters.push(<Route key={"UserActivity"} path="/UserActivity"
                                   component={UserActivity}/>);
  }
  if (authorities.includes("ResetUserPassword")) {
    userManagerRouters.push(<Route key={"ResetUserPassword"}
                                   path="/ResetUserPassword"
                                   component={ResetUserPassword}/>);
  }
  if (authorities.includes("UserRegistration")) {
    userManagerRouters.push(<Route key={"UserRegistration"}
                                   path="/UserRegistration"
                                   component={UserRegistration}/>);
  }
  if (authorities.includes("UserRole")) {
    userManagerRouters.push(<Route key={"UserRole"}
                                   path="/UserRole"
                                   component={UserRole}/>);
  }
  if (authorities.includes("UpdateUserRoles")) {
    userManagerRouters.push(<Route key={"UpdateUserRoles"}
                                   path="/UpdateUserRoles"
                                   component={UpdateUserRoles}/>);
  }
  if (authorities.includes("ChangeUserAccountStatus")) {
    userManagerRouters.push(<Route key={"ChangeUserAccountStatus"}
                                   path="/ChangeUserAccountStatus"
                                   component={ChangeUserAccountStatus}/>);
  }
  if (authorities.includes("DeleteUserAccount")) {
    userManagerRouters.push(<Route key={"DeleteUserAccount"}
                                   path="/DeleteUserAccount"
                                   component={DeleteUserAccount}/>);
  }
  if (authorities.includes("UserAccount")) {
    userManagerRouters.push(<Route key={"UserAccount"}
                                   path="/UserAccount"
                                   component={UserAccount}/>);
  }
  if (authorities.includes("UserEmailDomainMigration")) {
    userManagerRouters.push(<Route key={"UserEmailDomainMigration"}
                                   path="/UserEmailDomainMigration"
                                   component={UserEmailDomainMigration}/>);
  }
  if (authorities.includes("BatchOrderConfigParameter")) {
    configParameterRouters.push(<Route key={"BatchOrderConfigParameter"}
                                       path="/BatchOrderConfigParameter"
                                       component={BatchOrderConfigParameter}/>);
  }
  if (authorities.includes("FileManagerConfigParameter")) {
    configParameterRouters.push(<Route key={"FileManagerConfigParameter"}
                                       path="/FileManagerConfigParameter"
                                       component={FileManagerConfigParameter}/>);
  }
  if (authorities.includes("VoucherPolicyConfigParameter")) {
    configParameterRouters.push(<Route key={"VoucherPolicyConfigParameter"}
                                       path="/VoucherPolicyConfigParameter"
                                       component={VoucherPolicyConfigParameter}/>);
  }
  if (authorities.includes("UserManagementConfigParameter")) {
    configParameterRouters.push(<Route key={"UserManagementConfigParameter"}
                                       path="/UserManagementConfigParameter"
                                       component={UserManagementConfigParameter}/>);
  }
  if (authorities.includes("EmailConfigParameter")) {
    configParameterRouters.push(<Route key={"EmailConfigParameter"}
                                       path="/EmailConfigParameter"
                                       component={EmailConfigParameter}/>);
  }
  if (authorities.includes("OcsAccount")) {
    voucherConfigRouters.push(<Route key={"OcsAccount"} path="/OcsAccount"
                                     component={OcsAccount}/>);
  }
  if (authorities.includes("VoucherProvider")) {
    voucherConfigRouters.push(<Route key={"VoucherProvider"}
                                     path="/VoucherProvider"
                                     component={VoucherProvider}/>);
  }
  if (authorities.includes("VoucherPrinter")) {
    voucherConfigRouters.push(<Route key={"VoucherPrinter"}
                                     path="/VoucherPrinter"
                                     component={VoucherPrinter}/>);
  }
  if (authorities.includes("VoucherType")) {
    voucherConfigRouters.push(<Route key={"VoucherType"} path="/VoucherType"
                                     component={VoucherType}/>);
  }
  if (authorities.includes("Promotion")) {
    voucherConfigRouters.push(<Route key={"Promotion"} path="/Promotion"
                                     component={Promotion}/>);
  }
  if (authorities.includes("Taxation")) {
    voucherConfigRouters.push(<Route key={"Taxation"} path="/Taxation"
                                     component={Taxation}/>);
  }
  if (authorities.includes("RechargeValue")) {
    voucherConfigRouters.push(<Route key={"RechargeValue"} path="/RechargeValue"
                                     component={RechargeValue}/>);
  }
  if (authorities.includes("MsisdnProvider")) {
    voucherConfigRouters.push(<Route key={"MsisdnProvider"}
                                     path="/MsisdnProvider"
                                     component={MsisdnProvider}/>);
  }
  if (authorities.includes("ConsumableVoucherType")) {
    voucherConfigRouters.push(<Route key={"ConsumableVoucherType"}
                                     path="/ConsumableVoucherType"
                                     component={ConsumableVoucherType}/>);
  }
  if (authorities.includes("CreatedVoucherAdmin")) {
    voucherAdminRouters.push(<Route key={"CreatedVoucherAdmin"}
                                    path="/CreatedVoucherAdmin"
                                    component={CreatedVoucherAdmin}/>);
  }
  if (authorities.includes("ActiveVoucherAdmin")) {
    voucherAdminRouters.push(<Route key={"ActiveVoucherAdmin"}
                                    path="/ActiveVoucherAdmin"
                                    component={ActiveVoucherAdmin}/>);
  }
  if (authorities.includes("BlockedVoucherAdmin")) {
    voucherAdminRouters.push(<Route key={"BlockedVoucherAdmin"}
                                    path="/BlockedVoucherAdmin"
                                    component={BlockedVoucherAdmin}/>);
  }
  if (authorities.includes("PendingUsageVoucherAdmin")) {
    voucherAdminRouters.push(<Route key={"PendingUsageVoucherAdmin"}
                                    path="/PendingUsageVoucherAdmin"
                                    component={PendingUsageVoucherAdmin}/>);
  }
  if (authorities.includes("VoucherPolicySchedule")) {
    voucherPolicyRouters.push(<Route key={"VoucherPolicySchedule"}
                                     path="/VoucherPolicySchedule"
                                     component={VoucherPolicySchedule}/>);
  }
  if (authorities.includes("VoucherCreationBatchOrder")) {
    voucherProductionRouters.push(<Route key={"VoucherCreationBatchOrder"}
                                         path="/VoucherCreationBatchOrder"
                                         component={VoucherCreationBatchOrder}/>);
  }
  if (authorities.includes("ProductionFile")) {
    voucherProductionRouters.push(<Route key={"ProductionFile"}
                                         path="/ProductionFile"
                                         component={ProductionFile}/>);
  }
  if (authorities.includes("CreatedVoucherTransitionOrder")) {
    voucherTransitionRouters.push(<Route key={"CreatedVoucherTransitionOrder"}
                                         path="/CreatedVoucherTransitionOrder"
                                         component={CreatedVoucherTransitionOrder}/>);
  }
  if (authorities.includes("ActiveVoucherTransitionOrder")) {
    voucherTransitionRouters.push(<Route key={"ActiveVoucherTransitionOrder"}
                                         path="/ActiveVoucherTransitionOrder"
                                         component={ActiveVoucherTransitionOrder}/>);
  }
  if (authorities.includes("BlockedVoucherTransitionOrder")) {
    voucherTransitionRouters.push(<Route key={"BlockedVoucherTransitionOrder"}
                                         path="/BlockedVoucherTransitionOrder"
                                         component={BlockedVoucherTransitionOrder}/>);
  }
  if (authorities.includes("PendingUsageVoucherTransitionOrder")) {
    voucherTransitionRouters.push(<Route
        key={"PendingUsageVoucherTransitionOrder"}
        path="/PendingUsageVoucherTransitionOrder"
        component={PendingUsageVoucherTransitionOrder}/>);
  }
  if (authorities.includes("CreatedVoucherModificationOrder")) {
    voucherModificationRouters.push(<Route
        key={"CreatedVoucherModificationOrder"}
        path="/CreatedVoucherModificationOrder"
        component={CreatedVoucherModificationOrder}/>);
  }
  if (authorities.includes("BlockedVoucherModificationOrder")) {
    voucherModificationRouters.push(<Route
        key={"BlockedVoucherModificationOrder"}
        path="/BlockedVoucherModificationOrder"
        component={BlockedVoucherModificationOrder}/>);
  }

  if (authorities.includes("UserNotificationEmailHistory")) {
    systemHistoryRouters.push(<Route
        key={"UserNotificationEmailHistory"}
        path="/UserNotificationEmailHistory"
        component={UserNotificationEmailHistory}/>);
  }

  if (authorities.includes("RemoteFileTransferHistory")) {
    systemHistoryRouters.push(<Route
        key={"RemoteFileTransferHistory"}
        path="/RemoteFileTransferHistory"
        component={RemoteFileTransferHistory}/>);
  }

  if (authorities.includes("UserEmailDomainMigrationHistory")) {
    systemHistoryRouters.push(<Route
        key={"UserEmailDomainMigrationHistory"}
        path="/UserEmailDomainMigrationHistory"
        component={UserEmailDomainMigrationHistory}/>);
  }
  if (authorities.includes("VoucherPolicyHistory")) {
    systemHistoryRouters.push(<Route key={"VoucherPolicyHistory"}
                                     path="/VoucherPolicyHistory"
                                     component={VoucherPolicyHistory}/>);
  }
  if (authorities.includes("VoucherHistory")) {
    systemHistoryRouters.push(<Route key={"VoucherHistory"}
                                     path="/VoucherHistory"
                                     component={VoucherHistory}/>);
  }
  if (authorities.includes("BatchOrderHistory")) {
    systemHistoryRouters.push(<Route key={"BatchOrderHistory"}
                                     path="/BatchOrderHistory"
                                     component={BatchOrderHistory}/>);
  }
  if (authorities.includes("RechargeHistory")) {
    systemHistoryRouters.push(<Route key={"RechargeHistory"}
                                     path="/RechargeHistory"
                                     component={RechargeHistory}/>);
  }

  dashboardRouters.push(<Route
      key={"Dashboard"}
      path="/Dashboard"
      component={Dashboard}/>);

  rootRouters.push(<Redirect
      from={"/"}
      to={"/Dashboard"}/>);

  return (
      <Switch>
        {logoutRedirect.map((key) => (key))}
        {userManagerRouters.map((key) => (key))}
        {configParameterRouters.map((key) => (key))}
        {voucherAdminRouters.map((key) => (key))}
        {voucherConfigRouters.map((key) => (key))}
        {voucherProductionRouters.map((key) => (key))}
        {voucherTransitionRouters.map((key) => (key))}
        {voucherModificationRouters.map((key) => (key))}
        {voucherPolicyRouters.map((key) => (key))}
        {systemHistoryRouters.map((key) => (key))}
        {changePasswordRouter.map((key) => (key))}
        {userProfileRouter.map((key) => (key))}
        {dashboardRouters.map((key) => (key))}
        {rootRouters.map((key) => (key))}
      </Switch>
  );
};

export default withRouter(ContentRouter);
