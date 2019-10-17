import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './employee-time-sheet.reducer';
import { IEmployeeTimeSheet } from 'app/shared/model/employee-time-sheet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEmployeeTimeSheetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EmployeeTimeSheetDetail extends React.Component<IEmployeeTimeSheetDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { employeeTimeSheetEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            EmployeeTimeSheet [<b>{employeeTimeSheetEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="employeeId">Employee Id</span>
            </dt>
            <dd>{employeeTimeSheetEntity.employeeId}</dd>
            <dt>
              <span id="month">Month</span>
            </dt>
            <dd>{employeeTimeSheetEntity.month}</dd>
            <dt>
              <span id="year">Year</span>
            </dt>
            <dd>{employeeTimeSheetEntity.year}</dd>
            <dt>
              <span id="noOfWorkingDays">No Of Working Days</span>
            </dt>
            <dd>{employeeTimeSheetEntity.noOfWorkingDays}</dd>
            <dt>
              <span id="noOfLeavs">No Of Leavs</span>
            </dt>
            <dd>{employeeTimeSheetEntity.noOfLeavs}</dd>
            <dt>
              <span id="noOfLop">No Of Lop</span>
            </dt>
            <dd>{employeeTimeSheetEntity.noOfLop}</dd>
            <dt>
              <span id="noOfArearDays">No Of Arear Days</span>
            </dt>
            <dd>{employeeTimeSheetEntity.noOfArearDays}</dd>
            <dt>Employee Id</dt>
            <dd>{employeeTimeSheetEntity.employeeId ? employeeTimeSheetEntity.employeeId.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/employee-time-sheet" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/employee-time-sheet/${employeeTimeSheetEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ employeeTimeSheet }: IRootState) => ({
  employeeTimeSheetEntity: employeeTimeSheet.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeTimeSheetDetail);
