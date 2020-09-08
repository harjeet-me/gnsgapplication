import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IASProgram, ASProgram } from 'app/shared/model/as-program.model';
import { ASProgramService } from './as-program.service';
import { ASProgramComponent } from './as-program.component';
import { ASProgramDetailComponent } from './as-program-detail.component';
import { ASProgramUpdateComponent } from './as-program-update.component';

@Injectable({ providedIn: 'root' })
export class ASProgramResolve implements Resolve<IASProgram> {
  constructor(private service: ASProgramService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IASProgram> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((aSProgram: HttpResponse<ASProgram>) => {
          if (aSProgram.body) {
            return of(aSProgram.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ASProgram());
  }
}

export const aSProgramRoute: Routes = [
  {
    path: '',
    component: ASProgramComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams,
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gnsgapplicationApp.aSProgram.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ASProgramDetailComponent,
    resolve: {
      aSProgram: ASProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.aSProgram.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ASProgramUpdateComponent,
    resolve: {
      aSProgram: ASProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.aSProgram.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ASProgramUpdateComponent,
    resolve: {
      aSProgram: ASProgramResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gnsgapplicationApp.aSProgram.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
