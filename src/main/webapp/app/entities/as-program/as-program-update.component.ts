import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IASProgram, ASProgram } from 'app/shared/model/as-program.model';
import { ASProgramService } from './as-program.service';

@Component({
  selector: 'jhi-as-program-update',
  templateUrl: './as-program-update.component.html',
})
export class ASProgramUpdateComponent implements OnInit {
  isSaving = false;
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    program: [],
    family: [],
    phoneNumber: [],
    address: [],
    startDate: [],
    endDate: [],
    remark: [],
    bookingDate: [],
    desc: [],
    status: [],
    createdDate: [],
    createdBy: [],
    lastModifiedDate: [],
    lastModifiedBy: [],
  });

  constructor(protected aSProgramService: ASProgramService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aSProgram }) => {
      if (!aSProgram.id) {
        const today = moment().startOf('day');
        aSProgram.bookingDate = today;
        aSProgram.createdDate = today;
        aSProgram.lastModifiedDate = today;
      }

      this.updateForm(aSProgram);
    });
  }

  updateForm(aSProgram: IASProgram): void {
    this.editForm.patchValue({
      id: aSProgram.id,
      program: aSProgram.program,
      family: aSProgram.family,
      phoneNumber: aSProgram.phoneNumber,
      address: aSProgram.address,
      startDate: aSProgram.startDate,
      endDate: aSProgram.endDate,
      remark: aSProgram.remark,
      bookingDate: aSProgram.bookingDate ? aSProgram.bookingDate.format(DATE_TIME_FORMAT) : null,
      desc: aSProgram.desc,
      status: aSProgram.status,
      createdDate: aSProgram.createdDate ? aSProgram.createdDate.format(DATE_TIME_FORMAT) : null,
      createdBy: aSProgram.createdBy,
      lastModifiedDate: aSProgram.lastModifiedDate ? aSProgram.lastModifiedDate.format(DATE_TIME_FORMAT) : null,
      lastModifiedBy: aSProgram.lastModifiedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const aSProgram = this.createFromForm();
    if (aSProgram.id !== undefined) {
      this.subscribeToSaveResponse(this.aSProgramService.update(aSProgram));
    } else {
      this.subscribeToSaveResponse(this.aSProgramService.create(aSProgram));
    }
  }

  private createFromForm(): IASProgram {
    return {
      ...new ASProgram(),
      id: this.editForm.get(['id'])!.value,
      program: this.editForm.get(['program'])!.value,
      family: this.editForm.get(['family'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      address: this.editForm.get(['address'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      bookingDate: this.editForm.get(['bookingDate'])!.value
        ? moment(this.editForm.get(['bookingDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      desc: this.editForm.get(['desc'])!.value,
      status: this.editForm.get(['status'])!.value,
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IASProgram>>): void {
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
