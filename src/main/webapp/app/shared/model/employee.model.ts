export interface IEmployee {
  id?: number;
  org?: string;
  status?: string;
}

export const defaultValue: Readonly<IEmployee> = {};
