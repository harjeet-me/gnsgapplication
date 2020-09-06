import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IExpenseReport } from 'app/shared/model/expense-report.model';

@Component({
  selector: 'jhi-expense-report-detail',
  templateUrl: './expense-report-detail.component.html',
})
export class ExpenseReportDetailComponent implements OnInit {
  expenseReport: IExpenseReport | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expenseReport }) => (this.expenseReport = expenseReport));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
