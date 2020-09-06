import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { ASProgramUpdateComponent } from 'app/entities/as-program/as-program-update.component';
import { ASProgramService } from 'app/entities/as-program/as-program.service';
import { ASProgram } from 'app/shared/model/as-program.model';

describe('Component Tests', () => {
  describe('ASProgram Management Update Component', () => {
    let comp: ASProgramUpdateComponent;
    let fixture: ComponentFixture<ASProgramUpdateComponent>;
    let service: ASProgramService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [ASProgramUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ASProgramUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ASProgramUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ASProgramService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ASProgram(123);
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
        const entity = new ASProgram();
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
