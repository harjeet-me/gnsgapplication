import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { GnsgapplicationTestModule } from '../../../test.module';
import { PathReportDetailComponent } from 'app/entities/path-report/path-report-detail.component';
import { PathReport } from 'app/shared/model/path-report.model';

describe('Component Tests', () => {
  describe('PathReport Management Detail Component', () => {
    let comp: PathReportDetailComponent;
    let fixture: ComponentFixture<PathReportDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ pathReport: new PathReport(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [PathReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PathReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PathReportDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load pathReport on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pathReport).toEqual(jasmine.objectContaining({ id: 123 }));
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
