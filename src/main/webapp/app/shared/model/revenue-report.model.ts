import { Moment } from 'moment';
import { REVTYPE } from 'app/shared/model/enumerations/revtype.model';

export interface IRevenueReport {
  id?: number;
  revType?: REVTYPE;
  startDate?: Moment;
  endDate?: Moment;
  reportContentType?: string;
  report?: any;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class RevenueReport implements IRevenueReport {
  constructor(
    public id?: number,
    public revType?: REVTYPE,
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
