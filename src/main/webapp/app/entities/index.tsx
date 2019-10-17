import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Employee from './employee';
import EmployeeDetails from './employee-details';
import EmployeeEarning from './employee-earning';
import EmployeeDeductions from './employee-deductions';
import EmployeeTimeSheet from './employee-time-sheet';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/employee`} component={Employee} />
      <ErrorBoundaryRoute path={`${match.url}/employee-details`} component={EmployeeDetails} />
      <ErrorBoundaryRoute path={`${match.url}/employee-earning`} component={EmployeeEarning} />
      <ErrorBoundaryRoute path={`${match.url}/employee-deductions`} component={EmployeeDeductions} />
      <ErrorBoundaryRoute path={`${match.url}/employee-time-sheet`} component={EmployeeTimeSheet} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
