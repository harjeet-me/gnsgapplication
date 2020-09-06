import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDailyProgramReport } from 'app/shared/model/daily-program-report.model';
import { DailyProgramReportService } from './daily-program-report.service';

@Component({
  templateUrl: './daily-program-report-delete-dialog.component.html',
})
export class DailyProgramReportDeleteDialogComponent {
  dailyProgramReport?: IDailyProgramReport;

  constructor(
    protected dailyProgramReportService: DailyProgramReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dailyProgramReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dailyProgramReportListModification');
      this.activeModal.close();
    });
  }
}
