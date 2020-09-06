import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { PathReportUpdateComponent } from 'app/entities/path-report/path-report-update.component';
import { PathReportService } from 'app/entities/path-report/path-report.service';
import { PathReport } from 'app/shared/model/path-report.model';

describe('Component Tests', () => {
  describe('PathReport Management Update Component', () => {
    let comp: PathReportUpdateComponent;
    let fixture: ComponentFixture<PathReportUpdateComponent>;
    let service: PathReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [PathReportUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PathReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PathReport(123);
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
        const entity = new PathReport();
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
