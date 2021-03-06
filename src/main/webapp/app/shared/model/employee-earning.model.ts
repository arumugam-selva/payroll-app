import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeEarning {
  id?: number;
  effectiveDate?: Moment;
  basic?: number;
  hra?: number;
  conveyance?: number;
  leaveEncash?: number;
  specialAllowance?: number;
  reimbursement?: number;
  employee?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeEarning> = {};
