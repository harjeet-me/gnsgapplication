import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IExpenseReport, ExpenseReport } from 'app/shared/model/expense-report.model';
import { ExpenseReportService } from './expense-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-expense-report-update',
  templateUrl: './expense-report-update.component.html',
})
export class ExpenseReportUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    expType: [],
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
    protected expenseReportService: ExpenseReportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ expenseReport }) => {
      if (!expenseReport.id) {
        const today = moment().startOf('day');
        expenseReport.createdDate = today;
        expenseReport.lastModifiedDate = today;
      }

      this.updateForm(expenseReport);
    });
  }

  updateForm(expenseReport: IExpenseReport): void {
    this.editForm.patchValue({
      id: expenseReport.id,
      expType: expenseReport.expType,
      startDate: expenseReport.startDate,
      endDate: expenseReport.endDate,
      report: expenseReport.report,
      reportContentType: expenseReport.reportContentType,
      createdDate: expenseReport.createdDate ? expenseReport.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: expenseReport.createdBy,
      lastModifiedDate: expenseReport.lastModifiedDate ? expenseReport.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: expenseReport.lastModifiedBy,
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
    const expenseReport = this.createFromForm();
    if (expenseReport.id !== undefined) {
      this.subscribeToSaveResponse(this.expenseReportService.update(expenseReport));
    } else {
      this.subscribeToSaveResponse(this.expenseReportService.create(expenseReport));
    }
  }

  private createFromForm(): IExpenseReport {
    return {
      ...new ExpenseReport(),
      id: this.editForm.get(['id'])!.value,
      expType: this.editForm.get(['expType'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExpenseReport>>): void {
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
