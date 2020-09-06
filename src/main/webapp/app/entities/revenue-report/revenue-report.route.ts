import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IRevenueReport, RevenueReport } from 'app/shared/model/revenue-report.model';
import { RevenueReportService } from './revenue-report.service';
import { RevenueReportComponent } from './revenue-report.component';
import { RevenueReportDetailComponent } from './revenue-report-detail.component';
import { RevenueReportUpdateComponent } from './revenue-report-update.component';

@Injectable({ providedIn: 'root' })
export class RevenueReportResolve implements Resolve<IRevenueReport> {
  constructor(private service: RevenueReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRevenueReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((revenueReport: HttpResponse<RevenueReport>) => {
          if (revenueReport.body) {
            return of(revenueReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RevenueReport());
  }
}

export const revenueReportRoute: Routes = [
  {
    path: '',
    component: RevenueReportComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.revenueReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RevenueReportDetailComponent,
    resolve: {
      revenueReport: RevenueReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.revenueReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RevenueReportUpdateComponent,
    resolve: {
      revenueReport: RevenueReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.revenueReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RevenueReportUpdateComponent,
    resolve: {
      revenueReport: RevenueReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.revenueReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
