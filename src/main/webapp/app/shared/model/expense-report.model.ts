import { Moment } from 'moment';
import { EXPTYPE } from 'app/shared/model/enumerations/exptype.model';

export interface IExpenseReport {
  id?: number;
  expType?: EXPTYPE;
  startDate?: Moment;
  endDate?: Moment;
  reportContentType?: string;
  report?: any;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class ExpenseReport implements IExpenseReport {
  constructor(
    public id?: number,
    public expType?: EXPTYPE,
    public startDate?: Moment,
    public endDate?: Moment,
    public reportContentType?: string,
    public report?: any,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string
  ) {}
}
