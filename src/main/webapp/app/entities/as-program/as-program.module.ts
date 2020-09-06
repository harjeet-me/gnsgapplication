import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GnsgapplicationSharedModule } from 'app/shared/shared.module';
import { ASProgramComponent } from './as-program.component';
import { ASProgramDetailComponent } from './as-program-detail.component';
import { ASProgramUpdateComponent } from './as-program-update.component';
import { ASProgramDeleteDialogComponent } from './as-program-delete-dialog.component';
import { aSProgramRoute } from './as-program.route';

@NgModule({
  imports: [GnsgapplicationSharedModule, RouterModule.forChild(aSProgramRoute)],
  declarations: [ASProgramComponent, ASProgramDetailComponent, ASProgramUpdateComponent, ASProgramDeleteDialogComponent],
  entryComponents: [ASProgramDeleteDialogComponent],
})
export class GnsgapplicationASProgramModule {}
