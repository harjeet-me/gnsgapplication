import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRevenueReport } from 'app/shared/model/revenue-report.model';

type EntityResponseType = HttpResponse<IRevenueReport>;
type EntityArrayResponseType = HttpResponse<IRevenueReport[]>;

@Injectable({ providedIn: 'root' })
export class RevenueReportService {
  public resourceUrl = SERVER_API_URL + 'api/revenue-reports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/revenue-reports';

  constructor(protected http: HttpClient) {}

  create(revenueReport: IRevenueReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(revenueReport);
    return this.http
      .post<IRevenueReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(revenueReport: IRevenueReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(revenueReport);
    return this.http
      .put<IRevenueReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRevenueReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRevenueReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRevenueReport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(revenueReport: IRevenueReport): IRevenueReport {
    const copy: IRevenueReport = Object.assign({}, revenueReport, {
      startDate: revenueReport.startDate && revenueReport.startDate.isValid() ? revenueReport.startDate.format(DATE_FORMAT) : undefined,
      endDate: revenueReport.endDate && revenueReport.endDate.isValid() ? revenueReport.endDate.format(DATE_FORMAT) : undefined,
      createdDate: revenueReport.createdDate && revenueReport.createdDate.isValid() ? revenueReport.createdDate.toJSON() : undefined,
      lastModifiedDate:
        revenueReport.lastModifiedDate && revenueReport.lastModifiedDate.isValid() ? revenueReport.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((revenueReport: IRevenueReport) => {
        revenueReport.startDate = revenueReport.startDate ? moment(revenueReport.startDate) : undefined;
        revenueReport.endDate = revenueReport.endDate ? moment(revenueReport.endDate) : undefined;
        revenueReport.createdDate = revenueReport.createdDate ? moment(revenueReport.createdDate) : undefined;
        revenueReport.lastModifiedDate = revenueReport.lastModifiedDate ? moment(revenueReport.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
