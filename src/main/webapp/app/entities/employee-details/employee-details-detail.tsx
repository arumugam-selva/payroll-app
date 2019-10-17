import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-details.reducer';
import { IEmployeeDetails } from 'app/shared/model/employee-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeDetailsDetail extends React.Component<IEmployeeDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeeDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            EmployeeDetails [<b>{employeeDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="employeeId">Employee Id</span>
            </dt>
            <dd>{employeeDetailsEntity.employeeId}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{employeeDetailsEntity.email}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{employeeDetailsEntity.name}</dd>
            <dt>
              <span id="designation">Designation</span>
            </dt>
            <dd>{employeeDetailsEntity.designation}</dd>
            <dt>
              <span id="department">Department</span>
            </dt>
            <dd>{employeeDetailsEntity.department}</dd>
            <dt>
              <span id="dob">Dob</span>
            </dt>
            <dd>
              <TextFormat value={employeeDetailsEntity.dob} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="joiningDate">Joining Date</span>
            </dt>
            <dd>
              <TextFormat value={employeeDetailsEntity.joiningDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="panNo">Pan No</span>
            </dt>
            <dd>{employeeDetailsEntity.panNo}</dd>
            <dt>
              <span id="bankAccountNo">Bank Account No</span>
            </dt>
            <dd>{employeeDetailsEntity.bankAccountNo}</dd>
            <dt>
              <span id="gender">Gender</span>
            </dt>
            <dd>{employeeDetailsEntity.gender}</dd>
            <dt>
              <span id="bank">Bank</span>
            </dt>
            <dd>{employeeDetailsEntity.bank}</dd>
            <dt>
              <span id="location">Location</span>
            </dt>
            <dd>{employeeDetailsEntity.location}</dd>
            <dt>Employee Id</dt>
            <dd>{employeeDetailsEntity.employeeId ? employeeDetailsEntity.employeeId.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employee-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employee-details/${employeeDetailsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employeeDetails }: IRootState) => ({
  employeeDetailsEntity: employeeDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeDetailsDetail);
