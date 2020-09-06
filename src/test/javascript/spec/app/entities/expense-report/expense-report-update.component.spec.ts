import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { ExpenseReportUpdateComponent } from 'app/entities/expense-report/expense-report-update.component';
import { ExpenseReportService } from 'app/entities/expense-report/expense-report.service';
import { ExpenseReport } from 'app/shared/model/expense-report.model';

describe('Component Tests', () => {
  describe('ExpenseReport Management Update Component', () => {
    let comp: ExpenseReportUpdateComponent;
    let fixture: ComponentFixture<ExpenseReportUpdateComponent>;
    let service: ExpenseReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [ExpenseReportUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ExpenseReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpenseReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpenseReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpenseReport(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ExpenseReport();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
