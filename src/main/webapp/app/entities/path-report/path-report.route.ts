import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPathReport, PathReport } from 'app/shared/model/path-report.model';
import { PathReportService } from './path-report.service';
import { PathReportComponent } from './path-report.component';
import { PathReportDetailComponent } from './path-report-detail.component';
import { PathReportUpdateComponent } from './path-report-update.component';

@Injectable({ providedIn: 'root' })
export class PathReportResolve implements Resolve<IPathReport> {
  constructor(private service: PathReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPathReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pathReport: HttpResponse<PathReport>) => {
          if (pathReport.body) {
            return of(pathReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PathReport());
  }
}

export const pathReportRoute: Routes = [
  {
    path: '',
    component: PathReportComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.pathReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PathReportDetailComponent,
    resolve: {
      pathReport: PathReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.pathReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PathReportUpdateComponent,
    resolve: {
      pathReport: PathReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.pathReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PathReportUpdateComponent,
    resolve: {
      pathReport: PathReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.pathReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
