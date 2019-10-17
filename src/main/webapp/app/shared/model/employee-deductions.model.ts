import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeDeductions {
  id?: number;
  employeeId?: string;
  effectiveDate?: number;
  pf?: number;
  profTax?: number;
  incomeTax?: number;
  lop?: number;
  employeeId?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeDeductions> = {};
