import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IExpenseReport } from 'app/shared/model/expense-report.model';

type EntityResponseType = HttpResponse<IExpenseReport>;
type EntityArrayResponseType = HttpResponse<IExpenseReport[]>;

@Injectable({ providedIn: 'root' })
export class ExpenseReportService {
  public resourceUrl = SERVER_API_URL + 'api/expense-reports';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/expense-reports';

  constructor(protected http: HttpClient) {}

  create(expenseReport: IExpenseReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expenseReport);
    return this.http
      .post<IExpenseReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(expenseReport: IExpenseReport): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(expenseReport);
    return this.http
      .put<IExpenseReport>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExpenseReport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpenseReport[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExpenseReport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(expenseReport: IExpenseReport): IExpenseReport {
    const copy: IExpenseReport = Object.assign({}, expenseReport, {
      startDate: expenseReport.startDate && expenseReport.startDate.isValid() ? expenseReport.startDate.format(DATE_FORMAT) : undefined,
      endDate: expenseReport.endDate && expenseReport.endDate.isValid() ? expenseReport.endDate.format(DATE_FORMAT) : undefined,
      createdDate: expenseReport.createdDate && expenseReport.createdDate.isValid() ? expenseReport.createdDate.toJSON() : undefined,
      lastModifiedDate:
        expenseReport.lastModifiedDate && expenseReport.lastModifiedDate.isValid() ? expenseReport.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((expenseReport: IExpenseReport) => {
        expenseReport.startDate = expenseReport.startDate ? moment(expenseReport.startDate) : undefined;
        expenseReport.endDate = expenseReport.endDate ? moment(expenseReport.endDate) : undefined;
        expenseReport.createdDate = expenseReport.createdDate ? moment(expenseReport.createdDate) : undefined;
        expenseReport.lastModifiedDate = expenseReport.lastModifiedDate ? moment(expenseReport.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
