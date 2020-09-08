import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPathReport, PathReport } from 'app/shared/model/path-report.model';
import { PathReportService } from './path-report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ISevadar } from 'app/shared/model/sevadar.model';
import { SevadarService } from 'app/entities/sevadar/sevadar.service';

@Component({
  selector: 'jhi-path-report-update',
  templateUrl: './path-report-update.component.html',
})
export class PathReportUpdateComponent implements OnInit {
  isSaving = false;
  sevadars: ISevadar[] = [];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    searchBy: [],
    pathiName: [],
    pathType: [],
    startDate: [],
    endDate: [],
    report: [],
    reportContentType: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
    pathi: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected pathReportService: PathReportService,
    protected sevadarService: SevadarService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pathReport }) => {
      if (!pathReport.id) {
        const today = moment().startOf('day');
        pathReport.createdDate = today;
        pathReport.lastModifiedDate = today;
      }

      this.updateForm(pathReport);

      this.sevadarService.query().subscribe((res: HttpResponse<ISevadar[]>) => (this.sevadars = res.body || []));
    });
  }

  updateForm(pathReport: IPathReport): void {
    this.editForm.patchValue({
      id: pathReport.id,
      searchBy: pathReport.searchBy,
      pathiName: pathReport.pathiName,
      pathType: pathReport.pathType,
      startDate: pathReport.startDate,
      endDate: pathReport.endDate,
      report: pathReport.report,
      reportContentType: pathReport.reportContentType,
      createdDate: pathReport.createdDate ? pathReport.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: pathReport.createdBy,
      lastModifiedDate: pathReport.lastModifiedDate ? pathReport.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: pathReport.lastModifiedBy,
      pathi: pathReport.pathi,
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
    const pathReport = this.createFromForm();
    if (pathReport.id !== undefined) {
      this.subscribeToSaveResponse(this.pathReportService.update(pathReport));
    } else {
      this.subscribeToSaveResponse(this.pathReportService.create(pathReport));
    }
  }

  private createFromForm(): IPathReport {
    return {
      ...new PathReport(),
      id: this.editForm.get(['id'])!.value,
      searchBy: this.editForm.get(['searchBy'])!.value,
      pathiName: this.editForm.get(['pathiName'])!.value,
      pathType: this.editForm.get(['pathType'])!.value,
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
      pathi: this.editForm.get(['pathi'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPathReport>>): void {
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

  trackById(index: number, item: ISevadar): any {
    return item.id;
  }
}
