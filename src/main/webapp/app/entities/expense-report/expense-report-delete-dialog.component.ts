import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpenseReport } from 'app/shared/model/expense-report.model';
import { ExpenseReportService } from './expense-report.service';

@Component({
  templateUrl: './expense-report-delete-dialog.component.html',
})
export class ExpenseReportDeleteDialogComponent {
  expenseReport?: IExpenseReport;

  constructor(
    protected expenseReportService: ExpenseReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expenseReportService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expenseReportListModification');
      this.activeModal.close();
    });
  }
}
