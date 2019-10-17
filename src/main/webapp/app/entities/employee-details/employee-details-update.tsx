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
import { getEntity, updateEntity, createEntity, reset } from './employee-details.reducer';
import { IEmployeeDetails } from 'app/shared/model/employee-details.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeDetailsUpdateState {
  isNew: boolean;
  employeeIdId: string;
}

export class EmployeeDetailsUpdate extends React.Component<IEmployeeDetailsUpdateProps, IEmployeeDetailsUpdateState> {
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
      const { employeeDetailsEntity } = this.props;
      const entity = {
        ...employeeDetailsEntity,
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
    this.props.history.push('/entity/employee-details');
  };

  render() {
    const { employeeDetailsEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="payRollApplicationApp.employeeDetails.home.createOrEditLabel">Create or edit a EmployeeDetails</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeeDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-details-id">ID</Label>
                    <AvInput id="employee-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="emailLabel" for="employee-details-email">
                    Email
                  </Label>
                  <AvField id="employee-details-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="employee-details-name">
                    Name
                  </Label>
                  <AvField id="employee-details-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="designationLabel" for="employee-details-designation">
                    Designation
                  </Label>
                  <AvField id="employee-details-designation" type="text" name="designation" />
                </AvGroup>
                <AvGroup>
                  <Label id="departmentLabel" for="employee-details-department">
                    Department
                  </Label>
                  <AvField id="employee-details-department" type="string" className="form-control" name="department" />
                </AvGroup>
                <AvGroup>
                  <Label id="dobLabel" for="employee-details-dob">
                    Dob
                  </Label>
                  <AvField id="employee-details-dob" type="date" className="form-control" name="dob" />
                </AvGroup>
                <AvGroup>
                  <Label id="joiningDateLabel" for="employee-details-joiningDate">
                    Joining Date
                  </Label>
                  <AvField id="employee-details-joiningDate" type="date" className="form-control" name="joiningDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="panNoLabel" for="employee-details-panNo">
                    Pan No
                  </Label>
                  <AvField id="employee-details-panNo" type="text" name="panNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="bankAccountNoLabel" for="employee-details-bankAccountNo">
                    Bank Account No
                  </Label>
                  <AvField id="employee-details-bankAccountNo" type="text" name="bankAccountNo" />
                </AvGroup>
                <AvGroup>
                  <Label id="genderLabel" for="employee-details-gender">
                    Gender
                  </Label>
                  <AvField id="employee-details-gender" type="text" name="gender" />
                </AvGroup>
                <AvGroup>
                  <Label id="bankLabel" for="employee-details-bank">
                    Bank
                  </Label>
                  <AvField id="employee-details-bank" type="text" name="bank" />
                </AvGroup>
                <AvGroup>
                  <Label id="locationLabel" for="employee-details-location">
                    Location
                  </Label>
                  <AvField id="employee-details-location" type="text" name="location" />
                </AvGroup>
                <AvGroup>
                  <Label for="employee-details-employeeId">Employee Id</Label>
                  <AvInput id="employee-details-employeeId" type="select" className="form-control" name="employeeId.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/employee-details" replace color="info">
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
  employeeDetailsEntity: storeState.employeeDetails.entity,
  loading: storeState.employeeDetails.loading,
  updating: storeState.employeeDetails.updating,
  updateSuccess: storeState.employeeDetails.updateSuccess
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
)(EmployeeDetailsUpdate);
