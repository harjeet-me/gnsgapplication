import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GnsgapplicationSharedModule } from 'app/shared/shared.module';
import { DailyProgramReportComponent } from './daily-program-report.component';
import { DailyProgramReportDetailComponent } from './daily-program-report-detail.component';
import { DailyProgramReportUpdateComponent } from './daily-program-report-update.component';
import { DailyProgramReportDeleteDialogComponent } from './daily-program-report-delete-dialog.component';
import { dailyProgramReportRoute } from './daily-program-report.route';

@NgModule({
  imports: [GnsgapplicationSharedModule, RouterModule.forChild(dailyProgramReportRoute)],
  declarations: [
    DailyProgramReportComponent,
    DailyProgramReportDetailComponent,
    DailyProgramReportUpdateComponent,
    DailyProgramReportDeleteDialogComponent,
  ],
  entryComponents: [DailyProgramReportDeleteDialogComponent],
})
export class GnsgapplicationDailyProgramReportModule {}
