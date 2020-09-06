import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { RevenueReportUpdateComponent } from 'app/entities/revenue-report/revenue-report-update.component';
import { RevenueReportService } from 'app/entities/revenue-report/revenue-report.service';
import { RevenueReport } from 'app/shared/model/revenue-report.model';

describe('Component Tests', () => {
  describe('RevenueReport Management Update Component', () => {
    let comp: RevenueReportUpdateComponent;
    let fixture: ComponentFixture<RevenueReportUpdateComponent>;
    let service: RevenueReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [RevenueReportUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RevenueReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RevenueReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RevenueReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RevenueReport(123);
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
        const entity = new RevenueReport();
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
