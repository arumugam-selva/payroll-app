import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-deductions.reducer';
import { IEmployeeDeductions } from 'app/shared/model/employee-deductions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeDeductionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeDeductionsDetail extends React.Component<IEmployeeDeductionsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeeDeductionsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            EmployeeDeductions [<b>{employeeDeductionsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="effectiveDate">Effective Date</span>
            </dt>
            <dd>
              <TextFormat value={employeeDeductionsEntity.effectiveDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="pf">Pf</span>
            </dt>
            <dd>{employeeDeductionsEntity.pf}</dd>
            <dt>
              <span id="profTax">Prof Tax</span>
            </dt>
            <dd>{employeeDeductionsEntity.profTax}</dd>
            <dt>
              <span id="incomeTax">Income Tax</span>
            </dt>
            <dd>{employeeDeductionsEntity.incomeTax}</dd>
            <dt>
              <span id="lop">Lop</span>
            </dt>
            <dd>{employeeDeductionsEntity.lop}</dd>
            <dt>Employee</dt>
            <dd>{employeeDeductionsEntity.employee ? employeeDeductionsEntity.employee.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employee-deductions" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employee-deductions/${employeeDeductionsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employeeDeductions }: IRootState) => ({
  employeeDeductionsEntity: employeeDeductions.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeDeductionsDetail);
