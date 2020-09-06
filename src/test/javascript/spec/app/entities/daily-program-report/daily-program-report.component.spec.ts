import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GnsgapplicationTestModule } from '../../../test.module';
import { DailyProgramReportComponent } from 'app/entities/daily-program-report/daily-program-report.component';
import { DailyProgramReportService } from 'app/entities/daily-program-report/daily-program-report.service';
import { DailyProgramReport } from 'app/shared/model/daily-program-report.model';

describe('Component Tests', () => {
  describe('DailyProgramReport Management Component', () => {
    let comp: DailyProgramReportComponent;
    let fixture: ComponentFixture<DailyProgramReportComponent>;
    let service: DailyProgramReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [DailyProgramReportComponent],
      })
        .overrideTemplate(DailyProgramReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DailyProgramReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DailyProgramReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DailyProgramReport(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dailyProgramReports && comp.dailyProgramReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
