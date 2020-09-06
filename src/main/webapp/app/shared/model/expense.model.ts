import { Moment } from 'moment';
import { IVendor } from 'app/shared/model/vendor.model';
import { EXPTYPE } from 'app/shared/model/enumerations/exptype.model';

export interface IExpense {
  id?: number;
  expType?: EXPTYPE;
  amt?: number;
  date?: Moment;
  desc?: string;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  vendor?: IVendor;
}

export class Expense implements IExpense {
  constructor(
    public id?: number,
    public expType?: EXPTYPE,
    public amt?: number,
    public date?: Moment,
    public desc?: string,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public vendor?: IVendor
  ) {}
}
