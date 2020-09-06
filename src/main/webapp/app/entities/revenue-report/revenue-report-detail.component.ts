import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRevenueReport } from 'app/shared/model/revenue-report.model';

@Component({
  selector: 'jhi-revenue-report-detail',
  templateUrl: './revenue-report-detail.component.html',
})
export class RevenueReportDetailComponent implements OnInit {
  revenueReport: IRevenueReport | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ revenueReport }) => (this.revenueReport = revenueReport));
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
