import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IPathReport } from 'app/shared/model/path-report.model';

type EntityResponseType = HttpResponse<IPathReport>;
type EntityArrayResponseType = HttpResponse<IPathReport[]>;

@Injectable({ providedIn: 'root' })
export class PathReportService {
  public resourceUrl = SERVER_API_URL + 'api/path-reports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/path-reports';

  constructor(protected http: HttpClient) {}

  create(pathReport: IPathReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pathReport);
    return this.http
      .post<IPathReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pathReport: IPathReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pathReport);
    return this.http
      .put<IPathReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPathReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPathReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPathReport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(pathReport: IPathReport): IPathReport {
    const copy: IPathReport = Object.assign({}, pathReport, {
      startDate: pathReport.startDate && pathReport.startDate.isValid() ? pathReport.startDate.format(DATE_FORMAT) : undefined,
      endDate: pathReport.endDate && pathReport.endDate.isValid() ? pathReport.endDate.format(DATE_FORMAT) : undefined,
      createdDate: pathReport.createdDate && pathReport.createdDate.isValid() ? pathReport.createdDate.toJSON() : undefined,
      lastModifiedDate:
        pathReport.lastModifiedDate && pathReport.lastModifiedDate.isValid() ? pathReport.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((pathReport: IPathReport) => {
        pathReport.startDate = pathReport.startDate ? moment(pathReport.startDate) : undefined;
        pathReport.endDate = pathReport.endDate ? moment(pathReport.endDate) : undefined;
        pathReport.createdDate = pathReport.createdDate ? moment(pathReport.createdDate) : undefined;
        pathReport.lastModifiedDate = pathReport.lastModifiedDate ? moment(pathReport.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
