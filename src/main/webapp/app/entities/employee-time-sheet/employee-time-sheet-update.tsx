import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEmployee } from 'app/shared/model/employee.model';
import { getEntities as getEmployees } from 'app/entities/employee/employee.reducer';
import { getEntity, updateEntity, createEntity, reset } from './employee-time-sheet.reducer';
import { IEmployeeTimeSheet } from 'app/shared/model/employee-time-sheet.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeTimeSheetUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeTimeSheetUpdateState {
  isNew: boolean;
  employeeIdId: string;
}

export class EmployeeTimeSheetUpdate extends React.Component<IEmployeeTimeSheetUpdateProps, IEmployeeTimeSheetUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      employeeIdId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getEmployees();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { employeeTimeSheetEntity } = this.props;
      const entity = {
        ...employeeTimeSheetEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/employee-time-sheet');
  };

  render() {
    const { employeeTimeSheetEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="payRollApplicationApp.employeeTimeSheet.home.createOrEditLabel">Create or edit a EmployeeTimeSheet</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeeTimeSheetEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-time-sheet-id">ID</Label>
                    <AvInput id="employee-time-sheet-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="monthLabel" for="employee-time-sheet-month">
                    Month
                  </Label>
                  <AvField id="employee-time-sheet-month" type="string" className="form-control" name="month" />
                </AvGroup>
                <AvGroup>
                  <Label id="yearLabel" for="employee-time-sheet-year">
                    Year
                  </Label>
                  <AvField id="employee-time-sheet-year" type="string" className="form-control" name="year" />
                </AvGroup>
                <AvGroup>
                  <Label id="noOfWorkingDaysLabel" for="employee-time-sheet-noOfWorkingDays">
                    No Of Working Days
                  </Label>
                  <AvField id="employee-time-sheet-noOfWorkingDays" type="string" className="form-control" name="noOfWorkingDays" />
                </AvGroup>
                <AvGroup>
                  <Label id="noOfLeavsLabel" for="employee-time-sheet-noOfLeavs">
                    No Of Leavs
                  </Label>
                  <AvField id="employee-time-sheet-noOfLeavs" type="string" className="form-control" name="noOfLeavs" />
                </AvGroup>
                <AvGroup>
                  <Label id="noOfLopLabel" for="employee-time-sheet-noOfLop">
                    No Of Lop
                  </Label>
                  <AvField id="employee-time-sheet-noOfLop" type="string" className="form-control" name="noOfLop" />
                </AvGroup>
                <AvGroup>
                  <Label id="noOfArearDaysLabel" for="employee-time-sheet-noOfArearDays">
                    No Of Arear Days
                  </Label>
                  <AvField id="employee-time-sheet-noOfArearDays" type="string" className="form-control" name="noOfArearDays" />
                </AvGroup>
                <AvGroup>
                  <Label for="employee-time-sheet-employeeId">Employee Id</Label>
                  <AvInput id="employee-time-sheet-employeeId" type="select" className="form-control" name="employeeId.id">
                    <option value="" key="0" />
                    {employees
                      ? employees.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/employee-time-sheet" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  employees: storeState.employee.entities,
  employeeTimeSheetEntity: storeState.employeeTimeSheet.entity,
  loading: storeState.employeeTimeSheet.loading,
  updating: storeState.employeeTimeSheet.updating,
  updateSuccess: storeState.employeeTimeSheet.updateSuccess
});

const mapDispatchToProps = {
  getEmployees,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeTimeSheetUpdate);
