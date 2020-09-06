import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPathReport } from 'app/shared/model/path-report.model';
import { PathReportService } from './path-report.service';
import { PathReportDeleteDialogComponent } from './path-report-delete-dialog.component';

@Component({
  selector: 'jhi-path-report',
  templateUrl: './path-report.component.html',
})
export class PathReportComponent implements OnInit, OnDestroy {
  pathReports?: IPathReport[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected pathReportService: PathReportService,
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
      this.pathReportService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IPathReport[]>) => (this.pathReports = res.body || []));
      return;
    }

    this.pathReportService.query().subscribe((res: HttpResponse<IPathReport[]>) => (this.pathReports = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPathReports();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPathReport): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPathReports(): void {
    this.eventSubscriber = this.eventManager.subscribe('pathReportListModification', () => this.loadAll());
  }

  delete(pathReport: IPathReport): void {
    const modalRef = this.modalService.open(PathReportDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pathReport = pathReport;
  }
}
