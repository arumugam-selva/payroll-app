import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './employee-deductions.reducer';
import { IEmployeeDeductions } from 'app/shared/model/employee-deductions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IEmployeeDeductionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IEmployeeDeductionsState = IPaginationBaseState;

export class EmployeeDeductions extends React.Component<IEmployeeDeductionsProps, IEmployeeDeductionsState> {
  state: IEmployeeDeductionsState = {
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
    const { employeeDeductionsList, match } = this.props;
    return (
      <div>
        <h2 id="employee-deductions-heading">
          Employee Deductions
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Employee Deductions
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
            {employeeDeductionsList && employeeDeductionsList.length > 0 ? (
              <Table responsive aria-describedby="employee-deductions-heading">
                <thead>
                  <tr>
                    <th className="hand" onClick={this.sort('id')}>
                      ID <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('effectiveDate')}>
                      Effective Date <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('pf')}>
                      Pf <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('profTax')}>
                      Prof Tax <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('incomeTax')}>
                      Income Tax <FontAwesomeIcon icon="sort" />
                    </th>
                    <th className="hand" onClick={this.sort('lop')}>
                      Lop <FontAwesomeIcon icon="sort" />
                    </th>
                    <th>
                      Employee Id <FontAwesomeIcon icon="sort" />
                    </th>
                    <th />
                  </tr>
                </thead>
                <tbody>
                  {employeeDeductionsList.map((employeeDeductions, i) => (
                    <tr key={`entity-${i}`}>
                      <td>
                        <Button tag={Link} to={`${match.url}/${employeeDeductions.id}`} color="link" size="sm">
                          {employeeDeductions.id}
                        </Button>
                      </td>
                      <td>
                        <TextFormat type="date" value={employeeDeductions.effectiveDate} format={APP_LOCAL_DATE_FORMAT} />
                      </td>
                      <td>{employeeDeductions.pf}</td>
                      <td>{employeeDeductions.profTax}</td>
                      <td>{employeeDeductions.incomeTax}</td>
                      <td>{employeeDeductions.lop}</td>
                      <td>
                        {employeeDeductions.employeeId ? (
                          <Link to={`employee/${employeeDeductions.employeeId.id}`}>{employeeDeductions.employeeId.id}</Link>
                        ) : (
                          ''
                        )}
                      </td>
                      <td className="text-right">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`${match.url}/${employeeDeductions.id}`} color="info" size="sm">
                            <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeDeductions.id}/edit`} color="primary" size="sm">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button>
                          <Button tag={Link} to={`${match.url}/${employeeDeductions.id}/delete`} color="danger" size="sm">
                            <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                          </Button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </Table>
            ) : (
              <div className="alert alert-warning">No Employee Deductions found</div>
            )}
          </InfiniteScroll>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ employeeDeductions }: IRootState) => ({
  employeeDeductionsList: employeeDeductions.entities,
  totalItems: employeeDeductions.totalItems,
  links: employeeDeductions.links,
  entity: employeeDeductions.entity,
  updateSuccess: employeeDeductions.updateSuccess
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
)(EmployeeDeductions);
