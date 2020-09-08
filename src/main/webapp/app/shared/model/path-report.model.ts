import { Moment } from 'moment';
import { ISevadar } from 'app/shared/model/sevadar.model';
import { PATHSEARCHBY } from 'app/shared/model/enumerations/pathsearchby.model';
import { PROGTYPE } from 'app/shared/model/enumerations/progtype.model';

export interface IPathReport {
  id?: number;
  searchBy?: PATHSEARCHBY;
  pathiName?: string;
  pathType?: PROGTYPE;
  startDate?: Moment;
  endDate?: Moment;
  reportContentType?: string;
  report?: any;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  pathi?: ISevadar;
}

export class PathReport implements IPathReport {
  constructor(
    public id?: number,
    public searchBy?: PATHSEARCHBY,
    public pathiName?: string,
    public pathType?: PROGTYPE,
    public startDate?: Moment,
    public endDate?: Moment,
    public reportContentType?: string,
    public report?: any,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public pathi?: ISevadar
  ) {}
}
