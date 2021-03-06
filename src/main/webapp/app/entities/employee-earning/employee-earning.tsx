import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './employee-earning.reducer';
import { IEmployeeEarning } from 'app/shared/model/employee-earning.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmployeeEarningProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmployeeEarningState = IPaginationBaseState;

export class EmployeeEarning extends React.Component<IEmployeeEarningProps, IEmployeeEarningState> {
  state: IEmployeeEarningState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { employeeEarningList, match } = this.props;
    return (
      <div>
        <h2 id="employee-earning-heading">
          Employee Earnings
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Earning
          </Link>
        </h2>
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            {employeeEarningList && employeeEarningList.length > 0 ? (
              <Table responsive aria-describedby="employee-earning-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      ID <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('effectiveDate')}>
                      Effective Date <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('basic')}>
                      Basic <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('hra')}>
                      Hra <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('conveyance')}>
                      Conveyance <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('leaveEncash')}>
                      Leave Encash <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('specialAllowance')}>
                      Special Allowance <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('reimbursement')}>
                      Reimbursement <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      Employee <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {employeeEarningList.map((employeeEarning, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${employeeEarning.id}`} color="link" size="sm">
                          {employeeEarning.id}
                        </Button>
                      </td>
                      <td>
                        <TextFormat type="date" value={employeeEarning.effectiveDate} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>{employeeEarning.basic}</td>
                      <td>{employeeEarning.hra}</td>
                      <td>{employeeEarning.conveyance}</td>
                      <td>{employeeEarning.leaveEncash}</td>
                      <td>{employeeEarning.specialAllowance}</td>
                      <td>{employeeEarning.reimbursement}</td>
                      <td>
                        {employeeEarning.employee ? (
                          <Link to={`employee/${employeeEarning.employee.id}`}>{employeeEarning.employee.id}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${employeeEarning.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeEarning.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeEarning.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">No Employee Earnings found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employeeEarning }: IRootState) => ({
  employeeEarningList: employeeEarning.entities,
  totalItems: employeeEarning.totalItems,
  links: employeeEarning.links,
  entity: employeeEarning.entity,
  updateSuccess: employeeEarning.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EmployeeEarning);
