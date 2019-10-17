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
import { getEntity, updateEntity, createEntity, reset } from './employee-deductions.reducer';
import { IEmployeeDeductions } from 'app/shared/model/employee-deductions.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEmployeeDeductionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEmployeeDeductionsUpdateState {
  isNew: boolean;
  employeeIdId: string;
}

export class EmployeeDeductionsUpdate extends React.Component<IEmployeeDeductionsUpdateProps, IEmployeeDeductionsUpdateState> {
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
      const { employeeDeductionsEntity } = this.props;
      const entity = {
        ...employeeDeductionsEntity,
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
    this.props.history.push('/entity/employee-deductions');
  };

  render() {
    const { employeeDeductionsEntity, employees, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="payRollApplicationApp.employeeDeductions.home.createOrEditLabel">Create or edit a EmployeeDeductions</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : employeeDeductionsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="employee-deductions-id">ID</Label>
                    <AvInput id="employee-deductions-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="employeeIdLabel" for="employee-deductions-employeeId">
                    Employee Id
                  </Label>
                  <AvField id="employee-deductions-employeeId" type="string" className="form-control" name="employeeId" />
                </AvGroup>
                <AvGroup>
                  <Label id="effectiveDateLabel" for="employee-deductions-effectiveDate">
                    Effective Date
                  </Label>
                  <AvField id="employee-deductions-effectiveDate" type="string" className="form-control" name="effectiveDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="pfLabel" for="employee-deductions-pf">
                    Pf
                  </Label>
                  <AvField id="employee-deductions-pf" type="string" className="form-control" name="pf" />
                </AvGroup>
                <AvGroup>
                  <Label id="profTaxLabel" for="employee-deductions-profTax">
                    Prof Tax
                  </Label>
                  <AvField id="employee-deductions-profTax" type="string" className="form-control" name="profTax" />
                </AvGroup>
                <AvGroup>
                  <Label id="incomeTaxLabel" for="employee-deductions-incomeTax">
                    Income Tax
                  </Label>
                  <AvField id="employee-deductions-incomeTax" type="string" className="form-control" name="incomeTax" />
                </AvGroup>
                <AvGroup>
                  <Label id="lopLabel" for="employee-deductions-lop">
                    Lop
                  </Label>
                  <AvField id="employee-deductions-lop" type="string" className="form-control" name="lop" />
                </AvGroup>
                <AvGroup>
                  <Label for="employee-deductions-employeeId">Employee Id</Label>
                  <AvInput id="employee-deductions-employeeId" type="select" className="form-control" name="employeeId.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/employee-deductions" replace color="info">
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
  employeeDeductionsEntity: storeState.employeeDeductions.entity,
  loading: storeState.employeeDeductions.loading,
  updating: storeState.employeeDeductions.updating,
  updateSuccess: storeState.employeeDeductions.updateSuccess
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
)(EmployeeDeductionsUpdate);
