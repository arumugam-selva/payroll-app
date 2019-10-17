import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeDetails from './employee-details';
import EmployeeDetailsDetail from './employee-details-detail';
import EmployeeDetailsUpdate from './employee-details-update';
import EmployeeDetailsDeleteDialog from './employee-details-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeDetailsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeDetailsDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeDetails} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmployeeDetailsDeleteDialog} />
  </>
);

export default Routes;
