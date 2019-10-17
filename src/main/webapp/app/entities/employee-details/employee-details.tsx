import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './employee-details.reducer';
import { IEmployeeDetails } from 'app/shared/model/employee-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmployeeDetailsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmployeeDetailsState = IPaginationBaseState;

export class EmployeeDetails extends React.Component<IEmployeeDetailsProps, IEmployeeDetailsState> {
  state: IEmployeeDetailsState = {
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
    const { employeeDetailsList, match } = this.props;
    return (
      <div>
        <h2 id="employee-details-heading">
          Employee Details
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Details
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
            {employeeDetailsList && employeeDetailsList.length > 0 ? (
              <Table responsive aria-describedby="employee-details-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      ID <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('employeeId')}>
                      Employee Id <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('email')}>
                      Email <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('name')}>
                      Name <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('designation')}>
                      Designation <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('department')}>
                      Department <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('dob')}>
                      Dob <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('joiningDate')}>
                      Joining Date <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('panNo')}>
                      Pan No <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('bankAccountNo')}>
                      Bank Account No <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('gender')}>
                      Gender <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('bank')}>
                      Bank <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('location')}>
                      Location <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      Employee Id <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {employeeDetailsList.map((employeeDetails, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${employeeDetails.id}`} color="link" size="sm">
                          {employeeDetails.id}
                        </Button>
                      </td>
                      <td>{employeeDetails.employeeId}</td>
                      <td>{employeeDetails.email}</td>
                      <td>{employeeDetails.name}</td>
                      <td>{employeeDetails.designation}</td>
                      <td>{employeeDetails.department}</td>
                      <td>
                        <TextFormat type="date" value={employeeDetails.dob} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>
                        <TextFormat type="date" value={employeeDetails.joiningDate} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>{employeeDetails.panNo}</td>
                      <td>{employeeDetails.bankAccountNo}</td>
                      <td>{employeeDetails.gender}</td>
                      <td>{employeeDetails.bank}</td>
                      <td>{employeeDetails.location}</td>
                      <td>
                        {employeeDetails.employeeId ? (
                          <Link to={`employee/${employeeDetails.employeeId.id}`}>{employeeDetails.employeeId.id}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${employeeDetails.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeDetails.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeDetails.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">No Employee Details found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employeeDetails }: IRootState) => ({
  employeeDetailsList: employeeDetails.entities,
  totalItems: employeeDetails.totalItems,
  links: employeeDetails.links,
  entity: employeeDetails.entity,
  updateSuccess: employeeDetails.updateSuccess
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
)(EmployeeDetails);