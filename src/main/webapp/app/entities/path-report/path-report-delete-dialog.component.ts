import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPathReport } from 'app/shared/model/path-report.model';
import { PathReportService } from './path-report.service';

@Component({
  templateUrl: './path-report-delete-dialog.component.html',
})
export class PathReportDeleteDialogComponent {
  pathReport?: IPathReport;

  constructor(
    protected pathReportService: PathReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pathReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pathReportListModification');
      this.activeModal.close();
    });
  }
}
