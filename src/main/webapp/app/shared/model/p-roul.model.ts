import { Moment } from 'moment';
import { IASProgram } from 'app/shared/model/as-program.model';
import { ISevadar } from 'app/shared/model/sevadar.model';

export interface IPRoul {
  id?: number;
  pathiName?: string;
  desc?: string;
  totalRoul?: number;
  totalAmt?: number;
  bhogDate?: Moment;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  prog?: IASProgram;
  pathi?: ISevadar;
}

export class PRoul implements IPRoul {
  constructor(
    public id?: number,
    public pathiName?: string,
    public desc?: string,
    public totalRoul?: number,
    public totalAmt?: number,
    public bhogDate?: Moment,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public prog?: IASProgram,
    public pathi?: ISevadar
  ) {}
}
