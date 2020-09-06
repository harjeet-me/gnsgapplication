import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GnsgapplicationSharedModule } from 'app/shared/shared.module';
import { RevenueReportComponent } from './revenue-report.component';
import { RevenueReportDetailComponent } from './revenue-report-detail.component';
import { RevenueReportUpdateComponent } from './revenue-report-update.component';
import { RevenueReportDeleteDialogComponent } from './revenue-report-delete-dialog.component';
import { revenueReportRoute } from './revenue-report.route';

@NgModule({
  imports: [GnsgapplicationSharedModule, RouterModule.forChild(revenueReportRoute)],
  declarations: [RevenueReportComponent, RevenueReportDetailComponent, RevenueReportUpdateComponent, RevenueReportDeleteDialogComponent],
  entryComponents: [RevenueReportDeleteDialogComponent],
})
export class GnsgapplicationRevenueReportModule {}
