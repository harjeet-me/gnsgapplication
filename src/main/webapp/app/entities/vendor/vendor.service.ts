import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IVendor } from 'app/shared/model/vendor.model';

type EntityResponseType = HttpResponse<IVendor>;
type EntityArrayResponseType = HttpResponse<IVendor[]>;

@Injectable({ providedIn: 'root' })
export class VendorService {
  public resourceUrl = SERVER_API_URL + 'api/vendors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/vendors';

  constructor(protected http: HttpClient) {}

  create(vendor: IVendor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vendor);
    return this.http
      .post<IVendor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vendor: IVendor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vendor);
    return this.http
      .put<IVendor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVendor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVendor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVendor[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(vendor: IVendor): IVendor {
    const copy: IVendor = Object.assign({}, vendor, {
      createdDate: vendor.createdDate && vendor.createdDate.isValid() ? vendor.createdDate.toJSON() : undefined,
      lastModifiedDate: vendor.lastModifiedDate && vendor.lastModifiedDate.isValid() ? vendor.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vendor: IVendor) => {
        vendor.createdDate = vendor.createdDate ? moment(vendor.createdDate) : undefined;
        vendor.lastModifiedDate = vendor.lastModifiedDate ? moment(vendor.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
