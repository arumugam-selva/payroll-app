import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeEarning {
  id?: number;
  employeeId?: number;
  effectiveDate?: number;
  basic?: number;
  hra?: number;
  conveyance?: number;
  leaveEncash?: number;
  specialAllowance?: number;
  reimbursement?: number;
  employeeId?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeEarning> = {};
