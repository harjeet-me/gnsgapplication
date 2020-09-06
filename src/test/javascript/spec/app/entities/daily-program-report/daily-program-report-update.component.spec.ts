import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { DailyProgramReportUpdateComponent } from 'app/entities/daily-program-report/daily-program-report-update.component';
import { DailyProgramReportService } from 'app/entities/daily-program-report/daily-program-report.service';
import { DailyProgramReport } from 'app/shared/model/daily-program-report.model';

describe('Component Tests', () => {
  describe('DailyProgramReport Management Update Component', () => {
    let comp: DailyProgramReportUpdateComponent;
    let fixture: ComponentFixture<DailyProgramReportUpdateComponent>;
    let service: DailyProgramReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [DailyProgramReportUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DailyProgramReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyProgramReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyProgramReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DailyProgramReport(123);
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
        const entity = new DailyProgramReport();
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
