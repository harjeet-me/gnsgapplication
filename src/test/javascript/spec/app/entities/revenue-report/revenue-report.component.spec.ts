import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GnsgapplicationTestModule } from '../../../test.module';
import { RevenueReportComponent } from 'app/entities/revenue-report/revenue-report.component';
import { RevenueReportService } from 'app/entities/revenue-report/revenue-report.service';
import { RevenueReport } from 'app/shared/model/revenue-report.model';

describe('Component Tests', () => {
  describe('RevenueReport Management Component', () => {
    let comp: RevenueReportComponent;
    let fixture: ComponentFixture<RevenueReportComponent>;
    let service: RevenueReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [RevenueReportComponent],
      })
        .overrideTemplate(RevenueReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RevenueReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RevenueReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RevenueReport(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.revenueReports && comp.revenueReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
