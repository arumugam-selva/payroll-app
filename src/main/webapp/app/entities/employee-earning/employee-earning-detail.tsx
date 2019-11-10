import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-earning.reducer';
import { IEmployeeEarning } from 'app/shared/model/employee-earning.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeEarningDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeEarningDetail extends React.Component<IEmployeeEarningDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeeEarningEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            EmployeeEarning [<b>{employeeEarningEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="effectiveDate">Effective Date</span>
            </dt>
            <dd>
              <TextFormat value={employeeEarningEntity.effectiveDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="basic">Basic</span>
            </dt>
            <dd>{employeeEarningEntity.basic}</dd>
            <dt>
              <span id="hra">Hra</span>
            </dt>
            <dd>{employeeEarningEntity.hra}</dd>
            <dt>
              <span id="conveyance">Conveyance</span>
            </dt>
            <dd>{employeeEarningEntity.conveyance}</dd>
            <dt>
              <span id="leaveEncash">Leave Encash</span>
            </dt>
            <dd>{employeeEarningEntity.leaveEncash}</dd>
            <dt>
              <span id="specialAllowance">Special Allowance</span>
            </dt>
            <dd>{employeeEarningEntity.specialAllowance}</dd>
            <dt>
              <span id="reimbursement">Reimbursement</span>
            </dt>
            <dd>{employeeEarningEntity.reimbursement}</dd>
            <dt>Employee</dt>
            <dd>{employeeEarningEntity.employee ? employeeEarningEntity.employee.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employee-earning" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employee-earning/${employeeEarningEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employeeEarning }: IRootState) => ({
  employeeEarningEntity: employeeEarning.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeEarningDetail);
