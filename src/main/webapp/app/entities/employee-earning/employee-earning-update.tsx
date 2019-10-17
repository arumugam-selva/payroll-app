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
import { getEntity, updateEntity, createEntity, reset } from './employee-earning.reducer';
import { IEmployeeEarning } from 'app/shared/model/employee-earning.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeEarningUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeEarningUpdateState {
  isNew: boolean;
  employeeIdId: string;
}

export class EmployeeEarningUpdate extends React.Component<IEmployeeEarningUpdateProps, IEmployeeEarningUpdateState> {
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
      const { employeeEarningEntity } = this.props;
      const entity = {
        ...employeeEarningEntity,
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
    this.props.history.push('/entity/employee-earning');
  };

  render() {
    const { employeeEarningEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="payRollApplicationApp.employeeEarning.home.createOrEditLabel">Create or edit a EmployeeEarning</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeeEarningEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-earning-id">ID</Label>
                    <AvInput id="employee-earning-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="employeeIdLabel" for="employee-earning-employeeId">
                    Employee Id
                  </Label>
                  <AvField id="employee-earning-employeeId" type="string" className="form-control" name="employeeId" />
                </AvGroup>
                <AvGroup>
                  <Label id="effectiveDateLabel" for="employee-earning-effectiveDate">
                    Effective Date
                  </Label>
                  <AvField id="employee-earning-effectiveDate" type="string" className="form-control" name="effectiveDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="basicLabel" for="employee-earning-basic">
                    Basic
                  </Label>
                  <AvField id="employee-earning-basic" type="string" className="form-control" name="basic" />
                </AvGroup>
                <AvGroup>
                  <Label id="hraLabel" for="employee-earning-hra">
                    Hra
                  </Label>
                  <AvField id="employee-earning-hra" type="string" className="form-control" name="hra" />
                </AvGroup>
                <AvGroup>
                  <Label id="conveyanceLabel" for="employee-earning-conveyance">
                    Conveyance
                  </Label>
                  <AvField id="employee-earning-conveyance" type="string" className="form-control" name="conveyance" />
                </AvGroup>
                <AvGroup>
                  <Label id="leaveEncashLabel" for="employee-earning-leaveEncash">
                    Leave Encash
                  </Label>
                  <AvField id="employee-earning-leaveEncash" type="string" className="form-control" name="leaveEncash" />
                </AvGroup>
                <AvGroup>
                  <Label id="specialAllowanceLabel" for="employee-earning-specialAllowance">
                    Special Allowance
                  </Label>
                  <AvField id="employee-earning-specialAllowance" type="string" className="form-control" name="specialAllowance" />
                </AvGroup>
                <AvGroup>
                  <Label id="reimbursementLabel" for="employee-earning-reimbursement">
                    Reimbursement
                  </Label>
                  <AvField id="employee-earning-reimbursement" type="string" className="form-control" name="reimbursement" />
                </AvGroup>
                <AvGroup>
                  <Label for="employee-earning-employeeId">Employee Id</Label>
                  <AvInput id="employee-earning-employeeId" type="select" className="form-control" name="employeeId.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/employee-earning" replace color="info">
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
  employeeEarningEntity: storeState.employeeEarning.entity,
  loading: storeState.employeeEarning.loading,
  updating: storeState.employeeEarning.updating,
  updateSuccess: storeState.employeeEarning.updateSuccess
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
)(EmployeeEarningUpdate);
