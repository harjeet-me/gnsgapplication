import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IDailyProgramReport, DailyProgramReport } from 'app/shared/model/daily-program-report.model';
import { DailyProgramReportService } from './daily-program-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-daily-program-report-update',
  templateUrl: './daily-program-report-update.component.html',
})
export class DailyProgramReportUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    programType: [],
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
    protected dailyProgramReportService: DailyProgramReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dailyProgramReport }) => {
      if (!dailyProgramReport.id) {
        const today = moment().startOf('day');
        dailyProgramReport.createdDate = today;
        dailyProgramReport.lastModifiedDate = today;
      }

      this.updateForm(dailyProgramReport);
    });
  }

  updateForm(dailyProgramReport: IDailyProgramReport): void {
    this.editForm.patchValue({
      id: dailyProgramReport.id,
      programType: dailyProgramReport.programType,
      startDate: dailyProgramReport.startDate,
      endDate: dailyProgramReport.endDate,
      report: dailyProgramReport.report,
      reportContentType: dailyProgramReport.reportContentType,
      createdDate: dailyProgramReport.createdDate ? dailyProgramReport.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: dailyProgramReport.createdBy,
      lastModifiedDate: dailyProgramReport.lastModifiedDate ? dailyProgramReport.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: dailyProgramReport.lastModifiedBy,
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
    const dailyProgramReport = this.createFromForm();
    if (dailyProgramReport.id !== undefined) {
      this.subscribeToSaveResponse(this.dailyProgramReportService.update(dailyProgramReport));
    } else {
      this.subscribeToSaveResponse(this.dailyProgramReportService.create(dailyProgramReport));
    }
  }

  private createFromForm(): IDailyProgramReport {
    return {
      ...new DailyProgramReport(),
      id: this.editForm.get(['id'])!.value,
      programType: this.editForm.get(['programType'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDailyProgramReport>>): void {
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
