import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IRevenueReport, RevenueReport } from 'app/shared/model/revenue-report.model';
import { RevenueReportService } from './revenue-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-revenue-report-update',
  templateUrl: './revenue-report-update.component.html',
})
export class RevenueReportUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    revType: [],
    startDate: [],
    endDate: [],
    report: [],
    reportContentType: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected revenueReportService: RevenueReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ revenueReport }) => {
      if (!revenueReport.id) {
        const today = moment().startOf('day');
        revenueReport.createdDate = today;
        revenueReport.lastModifiedDate = today;
      }

      this.updateForm(revenueReport);
    });
  }

  updateForm(revenueReport: IRevenueReport): void {
    this.editForm.patchValue({
      id: revenueReport.id,
      revType: revenueReport.revType,
      startDate: revenueReport.startDate,
      endDate: revenueReport.endDate,
      report: revenueReport.report,
      reportContentType: revenueReport.reportContentType,
      createdDate: revenueReport.createdDate ? revenueReport.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: revenueReport.createdBy,
      lastModifiedDate: revenueReport.lastModifiedDate ? revenueReport.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: revenueReport.lastModifiedBy,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gnsgapplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const revenueReport = this.createFromForm();
    if (revenueReport.id !== undefined) {
      this.subscribeToSaveResponse(this.revenueReportService.update(revenueReport));
    } else {
      this.subscribeToSaveResponse(this.revenueReportService.create(revenueReport));
    }
  }

  private createFromForm(): IRevenueReport {
    return {
      ...new RevenueReport(),
      id: this.editForm.get(['id'])!.value,
      revType: this.editForm.get(['revType'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      reportContentType: this.editForm.get(['reportContentType'])!.value,
      report: this.editForm.get(['report'])!.value,
      createdDate: this.editForm.get(['createdDate'])!.value
        ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      lastModifiedDate: this.editForm.get(['lastModifiedDate'])!.value
        ? moment(this.editForm.get(['lastModifiedDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRevenueReport>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
