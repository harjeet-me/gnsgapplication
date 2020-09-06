import { Moment } from 'moment';
import { IPRoul } from 'app/shared/model/p-roul.model';
import { PROGTYPE } from 'app/shared/model/enumerations/progtype.model';
import { EventStatus } from 'app/shared/model/enumerations/event-status.model';

export interface IASProgram {
  id?: number;
  program?: PROGTYPE;
  family?: string;
  phoneNumber?: string;
  address?: string;
  startDate?: Moment;
  endDate?: Moment;
  remark?: string;
  bookingDate?: Moment;
  desc?: string;
  status?: EventStatus;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  pRouls?: IPRoul[];
}

export class ASProgram implements IASProgram {
  constructor(
    public id?: number,
    public program?: PROGTYPE,
    public family?: string,
    public phoneNumber?: string,
    public address?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public remark?: string,
    public bookingDate?: Moment,
    public desc?: string,
    public status?: EventStatus,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public pRouls?: IPRoul[]
  ) {}
}
