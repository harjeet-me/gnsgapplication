import { Moment } from 'moment';
import { IExpense } from 'app/shared/model/expense.model';

export interface IVendor {
  id?: number;
  name?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  expenses?: IExpense[];
}

export class Vendor implements IVendor {
  constructor(
    public id?: number,
    public name?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public expenses?: IExpense[]
  ) {}
}
