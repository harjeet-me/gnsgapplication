import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IASProgram } from 'app/shared/model/as-program.model';
import { ASProgramService } from './as-program.service';

@Component({
  templateUrl: './as-program-delete-dialog.component.html',
})
export class ASProgramDeleteDialogComponent {
  aSProgram?: IASProgram;

  constructor(protected aSProgramService: ASProgramService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.aSProgramService.delete(id).subscribe(() => {
      this.eventManager.broadcast('aSProgramListModification');
      this.activeModal.close();
    });
  }
}
