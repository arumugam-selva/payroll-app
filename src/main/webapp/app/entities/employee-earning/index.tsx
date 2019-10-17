import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeEarning from './employee-earning';
import EmployeeEarningDetail from './employee-earning-detail';
import EmployeeEarningUpdate from './employee-earning-update';
import EmployeeEarningDeleteDialog from './employee-earning-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeEarningUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeEarningUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeEarningDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeEarning} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmployeeEarningDeleteDialog} />
  </>
);

export default Routes;
