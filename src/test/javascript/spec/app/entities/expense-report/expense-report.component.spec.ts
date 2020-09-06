import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GnsgapplicationTestModule } from '../../../test.module';
import { ExpenseReportComponent } from 'app/entities/expense-report/expense-report.component';
import { ExpenseReportService } from 'app/entities/expense-report/expense-report.service';
import { ExpenseReport } from 'app/shared/model/expense-report.model';

describe('Component Tests', () => {
  describe('ExpenseReport Management Component', () => {
    let comp: ExpenseReportComponent;
    let fixture: ComponentFixture<ExpenseReportComponent>;
    let service: ExpenseReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GnsgapplicationTestModule],
        declarations: [ExpenseReportComponent],
      })
        .overrideTemplate(ExpenseReportComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpenseReportComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpenseReportService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExpenseReport(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.expenseReports && comp.expenseReports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
