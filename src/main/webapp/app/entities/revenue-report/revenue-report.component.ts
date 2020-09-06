import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRevenueReport } from 'app/shared/model/revenue-report.model';
import { RevenueReportService } from './revenue-report.service';
import { RevenueReportDeleteDialogComponent } from './revenue-report-delete-dialog.component';

@Component({
  selector: 'jhi-revenue-report',
  templateUrl: './revenue-report.component.html',
})
export class RevenueReportComponent implements OnInit, OnDestroy {
  revenueReports?: IRevenueReport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected revenueReportService: RevenueReportService,
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
      this.revenueReportService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IRevenueReport[]>) => (this.revenueReports = res.body || []));
      return;
    }

    this.revenueReportService.query().subscribe((res: HttpResponse<IRevenueReport[]>) => (this.revenueReports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRevenueReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRevenueReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInRevenueReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('revenueReportListModification', () => this.loadAll());
  }

  delete(revenueReport: IRevenueReport): void {
    const modalRef = this.modalService.open(RevenueReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.revenueReport = revenueReport;
  }
}
