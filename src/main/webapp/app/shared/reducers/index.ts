import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import employee, {
  EmployeeState
} from 'app/entities/employee/employee.reducer';
// prettier-ignore
import employeeDetails, {
  EmployeeDetailsState
} from 'app/entities/employee-details/employee-details.reducer';
// prettier-ignore
import employeeEarning, {
  EmployeeEarningState
} from 'app/entities/employee-earning/employee-earning.reducer';
// prettier-ignore
import employeeDeductions, {
  EmployeeDeductionsState
} from 'app/entities/employee-deductions/employee-deductions.reducer';
// prettier-ignore
import employeeTimeSheet, {
  EmployeeTimeSheetState
} from 'app/entities/employee-time-sheet/employee-time-sheet.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly employee: EmployeeState;
  readonly employeeDetails: EmployeeDetailsState;
  readonly employeeEarning: EmployeeEarningState;
  readonly employeeDeductions: EmployeeDeductionsState;
  readonly employeeTimeSheet: EmployeeTimeSheetState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  employee,
  employeeDetails,
  employeeEarning,
  employeeDeductions,
  employeeTimeSheet,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
