import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GnsgapplicationSharedModule } from 'app/shared/shared.module';
import { RevenueComponent } from './revenue.component';
import { RevenueDetailComponent } from './revenue-detail.component';
import { RevenueUpdateComponent } from './revenue-update.component';
import { RevenueDeleteDialogComponent } from './revenue-delete-dialog.component';
import { revenueRoute } from './revenue.route';

@NgModule({
  imports: [GnsgapplicationSharedModule, RouterModule.forChild(revenueRoute)],
  declarations: [RevenueComponent, RevenueDetailComponent, RevenueUpdateComponent, RevenueDeleteDialogComponent],
  entryComponents: [RevenueDeleteDialogComponent],
})
export class GnsgapplicationRevenueModule {}
