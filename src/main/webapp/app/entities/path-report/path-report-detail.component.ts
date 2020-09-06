import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPathReport } from 'app/shared/model/path-report.model';

@Component({
  selector: 'jhi-path-report-detail',
  templateUrl: './path-report-detail.component.html',
})
export class PathReportDetailComponent implements OnInit {
  pathReport: IPathReport | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pathReport }) => (this.pathReport = pathReport));
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
