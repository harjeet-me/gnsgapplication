import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GnsgapplicationTestModule } from '../../../test.module';
import { ASProgramDetailComponent } from 'app/entities/as-program/as-program-detail.component';
import { ASProgram } from 'app/shared/model/as-program.model';

describe('Component Tests', () => {
  describe('ASProgram Management Detail Component', () => {
    let comp: ASProgramDetailComponent;
    let fixture: ComponentFixture<ASProgramDetailComponent>;
    const route = ({ data: of({ aSProgram: new ASProgram(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [ASProgramDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ASProgramDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ASProgramDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load aSProgram on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aSProgram).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
