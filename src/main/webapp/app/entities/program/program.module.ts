import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GnsgapplicationSharedModule } from 'app/shared/shared.module';
import { ProgramComponent } from './program.component';
import { ProgramDetailComponent } from './program-detail.component';
import { ProgramUpdateComponent } from './program-update.component';
import { ProgramDeleteDialogComponent } from './program-delete-dialog.component';
import { programRoute } from './program.route';

@NgModule({
  imports: [GnsgapplicationSharedModule, RouterModule.forChild(programRoute)],
  declarations: [ProgramComponent, ProgramDetailComponent, ProgramUpdateComponent, ProgramDeleteDialogComponent],
  entryComponents: [ProgramDeleteDialogComponent],
})
export class GnsgapplicationProgramModule {}
