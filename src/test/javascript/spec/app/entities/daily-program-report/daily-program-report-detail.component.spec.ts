import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GnsgapplicationTestModule } from '../../../test.module';
import { DailyProgramReportDetailComponent } from 'app/entities/daily-program-report/daily-program-report-detail.component';
import { DailyProgramReport } from 'app/shared/model/daily-program-report.model';

describe('Component Tests', () => {
  describe('DailyProgramReport Management Detail Component', () => {
    let comp: DailyProgramReportDetailComponent;
    let fixture: ComponentFixture<DailyProgramReportDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ dailyProgramReport: new DailyProgramReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [DailyProgramReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DailyProgramReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DailyProgramReportDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load dailyProgramReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dailyProgramReport).toEqual(jasmine.objectContaining({ id: 123 }));
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
