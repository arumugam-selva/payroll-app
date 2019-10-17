import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './employee-time-sheet.reducer';
import { IEmployeeTimeSheet } from 'app/shared/model/employee-time-sheet.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmployeeTimeSheetProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmployeeTimeSheetState = IPaginationBaseState;

export class EmployeeTimeSheet extends React.Component<IEmployeeTimeSheetProps, IEmployeeTimeSheetState> {
  state: IEmployeeTimeSheetState = {
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
    const { employeeTimeSheetList, match } = this.props;
    return (
      <div>
        <h2 id="employee-time-sheet-heading">
          Employee Time Sheets
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Time Sheet
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
            {employeeTimeSheetList && employeeTimeSheetList.length > 0 ? (
              <Table responsive aria-describedby="employee-time-sheet-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      ID <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('month')}>
                      Month <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('year')}>
                      Year <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('noOfWorkingDays')}>
                      No Of Working Days <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('noOfLeavs')}>
                      No Of Leavs <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('noOfLop')}>
                      No Of Lop <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('noOfArearDays')}>
                      No Of Arear Days <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      Employee Id <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {employeeTimeSheetList.map((employeeTimeSheet, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${employeeTimeSheet.id}`} color="link" size="sm">
                          {employeeTimeSheet.id}
                        </Button>
                      </td>
                      <td>{employeeTimeSheet.month}</td>
                      <td>{employeeTimeSheet.year}</td>
                      <td>{employeeTimeSheet.noOfWorkingDays}</td>
                      <td>{employeeTimeSheet.noOfLeavs}</td>
                      <td>{employeeTimeSheet.noOfLop}</td>
                      <td>{employeeTimeSheet.noOfArearDays}</td>
                      <td>
                        {employeeTimeSheet.employeeId ? (
                          <Link to={`employee/${employeeTimeSheet.employeeId.id}`}>{employeeTimeSheet.employeeId.id}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${employeeTimeSheet.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeTimeSheet.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeTimeSheet.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">No Employee Time Sheets found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employeeTimeSheet }: IRootState) => ({
  employeeTimeSheetList: employeeTimeSheet.entities,
  totalItems: employeeTimeSheet.totalItems,
  links: employeeTimeSheet.links,
  entity: employeeTimeSheet.entity,
  updateSuccess: employeeTimeSheet.updateSuccess
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
)(EmployeeTimeSheet);
