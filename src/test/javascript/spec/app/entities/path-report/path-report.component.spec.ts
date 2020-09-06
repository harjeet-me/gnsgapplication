import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GnsgapplicationTestModule } from '../../../test.module';
import { PathReportComponent } from 'app/entities/path-report/path-report.component';
import { PathReportService } from 'app/entities/path-report/path-report.service';
import { PathReport } from 'app/shared/model/path-report.model';

describe('Component Tests', () => {
  describe('PathReport Management Component', () => {
    let comp: PathReportComponent;
    let fixture: ComponentFixture<PathReportComponent>;
    let service: PathReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [PathReportComponent],
      })
        .overrideTemplate(PathReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PathReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PathReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PathReport(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pathReports && comp.pathReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
