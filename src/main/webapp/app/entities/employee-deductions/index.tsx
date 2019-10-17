import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeDeductions from './employee-deductions';
import EmployeeDeductionsDetail from './employee-deductions-detail';
import EmployeeDeductionsUpdate from './employee-deductions-update';
import EmployeeDeductionsDeleteDialog from './employee-deductions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeDeductionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeDeductionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeDeductionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeDeductions} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmployeeDeductionsDeleteDialog} />
  </>
);

export default Routes;
