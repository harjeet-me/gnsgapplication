import { Moment } from 'moment';
import { IProgram } from 'app/shared/model/program.model';
import { IPathReport } from 'app/shared/model/path-report.model';

export interface ISevadar {
  id?: number;
  name?: string;
  email?: string;
  phoneNumber?: string;
  address?: string;
  sevaStartDate?: Moment;
  sevaEndDate?: Moment;
  isValid?: boolean;
  createdDate?: Moment;
  createdBy?: string;
  lastModifiedDate?: Moment;
  lastModifiedBy?: string;
  programs?: IProgram[];
  pathReports?: IPathReport[];
}

export class Sevadar implements ISevadar {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public phoneNumber?: string,
    public address?: string,
    public sevaStartDate?: Moment,
    public sevaEndDate?: Moment,
    public isValid?: boolean,
    public createdDate?: Moment,
    public createdBy?: string,
    public lastModifiedDate?: Moment,
    public lastModifiedBy?: string,
    public programs?: IProgram[],
    public pathReports?: IPathReport[]
  ) {
    this.isValid = this.isValid || false;
  }
}
