import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExpenseReport, ExpenseReport } from 'app/shared/model/expense-report.model';
import { ExpenseReportService } from './expense-report.service';
import { ExpenseReportComponent } from './expense-report.component';
import { ExpenseReportDetailComponent } from './expense-report-detail.component';
import { ExpenseReportUpdateComponent } from './expense-report-update.component';

@Injectable({ providedIn: 'root' })
export class ExpenseReportResolve implements Resolve<IExpenseReport> {
  constructor(private service: ExpenseReportService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExpenseReport> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((expenseReport: HttpResponse<ExpenseReport>) => {
          if (expenseReport.body) {
            return of(expenseReport.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExpenseReport());
  }
}

export const expenseReportRoute: Routes = [
  {
    path: '',
    component: ExpenseReportComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.expenseReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExpenseReportDetailComponent,
    resolve: {
      expenseReport: ExpenseReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.expenseReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExpenseReportUpdateComponent,
    resolve: {
      expenseReport: ExpenseReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.expenseReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExpenseReportUpdateComponent,
    resolve: {
      expenseReport: ExpenseReportResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.expenseReport.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
