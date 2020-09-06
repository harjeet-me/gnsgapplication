import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDailyProgramReport } from 'app/shared/model/daily-program-report.model';
import { DailyProgramReportService } from './daily-program-report.service';
import { DailyProgramReportDeleteDialogComponent } from './daily-program-report-delete-dialog.component';

@Component({
  selector: 'jhi-daily-program-report',
  templateUrl: './daily-program-report.component.html',
})
export class DailyProgramReportComponent implements OnInit, OnDestroy {
  dailyProgramReports?: IDailyProgramReport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected dailyProgramReportService: DailyProgramReportService,
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
      this.dailyProgramReportService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IDailyProgramReport[]>) => (this.dailyProgramReports = res.body || []));
      return;
    }

    this.dailyProgramReportService
      .query()
      .subscribe((res: HttpResponse<IDailyProgramReport[]>) => (this.dailyProgramReports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDailyProgramReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDailyProgramReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInDailyProgramReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('dailyProgramReportListModification', () => this.loadAll());
  }

  delete(dailyProgramReport: IDailyProgramReport): void {
    const modalRef = this.modalService.open(DailyProgramReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dailyProgramReport = dailyProgramReport;
  }
}
