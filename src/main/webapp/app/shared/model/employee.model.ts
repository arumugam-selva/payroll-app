import { Organization } from 'app/shared/model/enumerations/organization.model';

export interface IEmployee {
  id?: number;
  org?: Organization;
  status?: string;
}

export const defaultValue: Readonly<IEmployee> = {};
