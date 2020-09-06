import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GnsgapplicationTestModule } from '../../../test.module';
import { RevenueReportDetailComponent } from 'app/entities/revenue-report/revenue-report-detail.component';
import { RevenueReport } from 'app/shared/model/revenue-report.model';

describe('Component Tests', () => {
  describe('RevenueReport Management Detail Component', () => {
    let comp: RevenueReportDetailComponent;
    let fixture: ComponentFixture<RevenueReportDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ revenueReport: new RevenueReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [RevenueReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RevenueReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RevenueReportDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load revenueReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.revenueReport).toEqual(jasmine.objectContaining({ id: 123 }));
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
