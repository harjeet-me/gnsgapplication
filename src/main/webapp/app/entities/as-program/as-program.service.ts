import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IASProgram } from 'app/shared/model/as-program.model';

type EntityResponseType = HttpResponse<IASProgram>;
type EntityArrayResponseType = HttpResponse<IASProgram[]>;

@Injectable({ providedIn: 'root' })
export class ASProgramService {
  public resourceUrl = SERVER_API_URL + 'api/as-programs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/as-programs';

  constructor(protected http: HttpClient) {}

  create(aSProgram: IASProgram): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aSProgram);
    return this.http
      .post<IASProgram>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(aSProgram: IASProgram): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(aSProgram);
    return this.http
      .put<IASProgram>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IASProgram>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IASProgram[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IASProgram[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(aSProgram: IASProgram): IASProgram {
    const copy: IASProgram = Object.assign({}, aSProgram, {
      startDate: aSProgram.startDate && aSProgram.startDate.isValid() ? aSProgram.startDate.format(DATE_FORMAT) : undefined,
      endDate: aSProgram.endDate && aSProgram.endDate.isValid() ? aSProgram.endDate.format(DATE_FORMAT) : undefined,
      bookingDate: aSProgram.bookingDate && aSProgram.bookingDate.isValid() ? aSProgram.bookingDate.toJSON() : undefined,
      createdDate: aSProgram.createdDate && aSProgram.createdDate.isValid() ? aSProgram.createdDate.toJSON() : undefined,
      lastModifiedDate:
        aSProgram.lastModifiedDate && aSProgram.lastModifiedDate.isValid() ? aSProgram.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? moment(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
      res.body.bookingDate = res.body.bookingDate ? moment(res.body.bookingDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((aSProgram: IASProgram) => {
        aSProgram.startDate = aSProgram.startDate ? moment(aSProgram.startDate) : undefined;
        aSProgram.endDate = aSProgram.endDate ? moment(aSProgram.endDate) : undefined;
        aSProgram.bookingDate = aSProgram.bookingDate ? moment(aSProgram.bookingDate) : undefined;
        aSProgram.createdDate = aSProgram.createdDate ? moment(aSProgram.createdDate) : undefined;
        aSProgram.lastModifiedDate = aSProgram.lastModifiedDate ? moment(aSProgram.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
