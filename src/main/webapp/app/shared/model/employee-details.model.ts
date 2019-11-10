import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface IEmployeeDetails {
  id?: number;
  email?: string;
  name?: string;
  designation?: string;
  department?: string;
  dob?: Moment;
  joiningDate?: Moment;
  panNo?: string;
  bankAccountNo?: string;
  gender?: string;
  bank?: string;
  location?: string;
  employee?: IEmployee;
}

export const defaultValue: Readonly<IEmployeeDetails> = {};
