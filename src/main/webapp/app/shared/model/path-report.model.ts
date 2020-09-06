import { Moment } from 'moment';
import { PROGTYPE } from 'app/shared/model/enumerations/progtype.model';

export interface IPathReport {
  id?: number;
  pathType?: PROGTYPE;
  startDate?: Moment;
  endDate?: Moment;
  reportContentType?: string;
  report?: any;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class PathReport implements IPathReport {
  constructor(
    public id?: number,
    public pathType?: PROGTYPE,
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
