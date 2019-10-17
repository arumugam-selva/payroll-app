import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeDetails {
  id?: number;
  employeeId?: string;
  email?: string;
  name?: string;
  designation?: string;
  department?: number;
  dob?: Moment;
  joiningDate?: Moment;
  panNo?: string;
  bankAccountNo?: string;
  gender?: string;
  bank?: string;
  location?: string;
  employeeId?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeDetails> = {};
