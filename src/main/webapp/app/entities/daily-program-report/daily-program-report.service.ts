import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDailyProgramReport } from 'app/shared/model/daily-program-report.model';

type EntityResponseType = HttpResponse<IDailyProgramReport>;
type EntityArrayResponseType = HttpResponse<IDailyProgramReport[]>;

@Injectable({ providedIn: 'root' })
export class DailyProgramReportService {
  public resourceUrl = SERVER_API_URL + 'api/daily-program-reports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/daily-program-reports';

  constructor(protected http: HttpClient) {}

  create(dailyProgramReport: IDailyProgramReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyProgramReport);
    return this.http
      .post<IDailyProgramReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dailyProgramReport: IDailyProgramReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dailyProgramReport);
    return this.http
      .put<IDailyProgramReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDailyProgramReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyProgramReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDailyProgramReport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(dailyProgramReport: IDailyProgramReport): IDailyProgramReport {
    const copy: IDailyProgramReport = Object.assign({}, dailyProgramReport, {
      startDate:
        dailyProgramReport.startDate && dailyProgramReport.startDate.isValid()
          ? dailyProgramReport.startDate.format(DATE_FORMAT)
          : undefined,
      endDate:
        dailyProgramReport.endDate && dailyProgramReport.endDate.isValid() ? dailyProgramReport.endDate.format(DATE_FORMAT) : undefined,
      createdDate:
        dailyProgramReport.createdDate && dailyProgramReport.createdDate.isValid() ? dailyProgramReport.createdDate.toJSON() : undefined,
      lastModifiedDate:
        dailyProgramReport.lastModifiedDate && dailyProgramReport.lastModifiedDate.isValid()
          ? dailyProgramReport.lastModifiedDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dailyProgramReport: IDailyProgramReport) => {
        dailyProgramReport.startDate = dailyProgramReport.startDate ? moment(dailyProgramReport.startDate) : undefined;
        dailyProgramReport.endDate = dailyProgramReport.endDate ? moment(dailyProgramReport.endDate) : undefined;
        dailyProgramReport.createdDate = dailyProgramReport.createdDate ? moment(dailyProgramReport.createdDate) : undefined;
        dailyProgramReport.lastModifiedDate = dailyProgramReport.lastModifiedDate ? moment(dailyProgramReport.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
