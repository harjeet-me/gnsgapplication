import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDailyProgramReport, DailyProgramReport } from 'app/shared/model/daily-program-report.model';
import { DailyProgramReportService } from './daily-program-report.service';
import { DailyProgramReportComponent } from './daily-program-report.component';
import { DailyProgramReportDetailComponent } from './daily-program-report-detail.component';
import { DailyProgramReportUpdateComponent } from './daily-program-report-update.component';

@Injectable({ providedIn: 'root' })
export class DailyProgramReportResolve implements Resolve<IDailyProgramReport> {
  constructor(private service: DailyProgramReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDailyProgramReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dailyProgramReport: HttpResponse<DailyProgramReport>) => {
          if (dailyProgramReport.body) {
            return of(dailyProgramReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DailyProgramReport());
  }
}

export const dailyProgramReportRoute: Routes = [
  {
    path: '',
    component: DailyProgramReportComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.dailyProgramReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DailyProgramReportDetailComponent,
    resolve: {
      dailyProgramReport: DailyProgramReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.dailyProgramReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DailyProgramReportUpdateComponent,
    resolve: {
      dailyProgramReport: DailyProgramReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.dailyProgramReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DailyProgramReportUpdateComponent,
    resolve: {
      dailyProgramReport: DailyProgramReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.dailyProgramReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
