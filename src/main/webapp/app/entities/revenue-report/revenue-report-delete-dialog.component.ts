import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRevenueReport } from 'app/shared/model/revenue-report.model';
import { RevenueReportService } from './revenue-report.service';

@Component({
  templateUrl: './revenue-report-delete-dialog.component.html',
})
export class RevenueReportDeleteDialogComponent {
  revenueReport?: IRevenueReport;

  constructor(
    protected revenueReportService: RevenueReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.revenueReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('revenueReportListModification');
      this.activeModal.close();
    });
  }
}
