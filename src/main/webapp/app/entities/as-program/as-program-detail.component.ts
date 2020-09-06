import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IASProgram } from 'app/shared/model/as-program.model';

@Component({
  selector: 'jhi-as-program-detail',
  templateUrl: './as-program-detail.component.html',
})
export class ASProgramDetailComponent implements OnInit {
  aSProgram: IASProgram | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ aSProgram }) => (this.aSProgram = aSProgram));
  }

  previousState(): void {
    window.history.back();
  }
}
