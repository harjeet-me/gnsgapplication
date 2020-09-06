import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GnsgapplicationTestModule } from '../../../test.module';
import { ExpenseReportDetailComponent } from 'app/entities/expense-report/expense-report-detail.component';
import { ExpenseReport } from 'app/shared/model/expense-report.model';

describe('Component Tests', () => {
  describe('ExpenseReport Management Detail Component', () => {
    let comp: ExpenseReportDetailComponent;
    let fixture: ComponentFixture<ExpenseReportDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ expenseReport: new ExpenseReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [ExpenseReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpenseReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpenseReportDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load expenseReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expenseReport).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
