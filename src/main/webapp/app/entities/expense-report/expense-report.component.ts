import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IExpenseReport } from 'app/shared/model/expense-report.model';
import { ExpenseReportService } from './expense-report.service';
import { ExpenseReportDeleteDialogComponent } from './expense-report-delete-dialog.component';

@Component({
  selector: 'jhi-expense-report',
  templateUrl: './expense-report.component.html',
})
export class ExpenseReportComponent implements OnInit, OnDestroy {
  expenseReports?: IExpenseReport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected expenseReportService: ExpenseReportService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.expenseReportService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IExpenseReport[]>) => (this.expenseReports = res.body || []));
      return;
    }

    this.expenseReportService.query().subscribe((res: HttpResponse<IExpenseReport[]>) => (this.expenseReports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInExpenseReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IExpenseReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInExpenseReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('expenseReportListModification', () => this.loadAll());
  }

  delete(expenseReport: IExpenseReport): void {
    const modalRef = this.modalService.open(ExpenseReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.expenseReport = expenseReport;
  }
}
