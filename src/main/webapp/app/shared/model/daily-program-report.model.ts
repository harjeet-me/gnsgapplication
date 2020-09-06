import { Moment } from 'moment';
import { EVENTTYPE } from 'app/shared/model/enumerations/eventtype.model';

export interface IDailyProgramReport {
  id?: number;
  programType?: EVENTTYPE;
  startDate?: Moment;
  endDate?: Moment;
  reportContentType?: string;
  report?: any;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
}

export class DailyProgramReport implements IDailyProgramReport {
  constructor(
    public id?: number,
    public programType?: EVENTTYPE,
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
