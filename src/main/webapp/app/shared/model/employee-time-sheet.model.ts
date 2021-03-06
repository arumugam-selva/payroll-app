import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeTimeSheet {
  id?: number;
  month?: number;
  year?: number;
  noOfWorkingDays?: number;
  noOfLeavs?: number;
  noOfLop?: number;
  noOfArearDays?: number;
  employee?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeTimeSheet> = {};
