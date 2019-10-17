import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeeTimeSheet from './employee-time-sheet';
import EmployeeTimeSheetDetail from './employee-time-sheet-detail';
import EmployeeTimeSheetUpdate from './employee-time-sheet-update';
import EmployeeTimeSheetDeleteDialog from './employee-time-sheet-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeeTimeSheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeeTimeSheetUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeeTimeSheetDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeeTimeSheet} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmployeeTimeSheetDeleteDialog} />
  </>
);

export default Routes;
