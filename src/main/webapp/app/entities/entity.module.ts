import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'program',
        loadChildren: () => import('./program/program.module').then(m => m.GnsgapplicationProgramModule),
      },
      {
        path: 'daily-program-report',
        loadChildren: () =>
          import('./daily-program-report/daily-program-report.module').then(m => m.GnsgapplicationDailyProgramReportModule),
      },
      {
        path: 'path-report',
        loadChildren: () => import('./path-report/path-report.module').then(m => m.GnsgapplicationPathReportModule),
      },
      {
        path: 'as-program',
        loadChildren: () => import('./as-program/as-program.module').then(m => m.GnsgapplicationASProgramModule),
      },
      {
        path: 'expense-report',
        loadChildren: () => import('./expense-report/expense-report.module').then(m => m.GnsgapplicationExpenseReportModule),
      },
      {
        path: 'expense',
        loadChildren: () => import('./expense/expense.module').then(m => m.GnsgapplicationExpenseModule),
      },
      {
        path: 'vendor',
        loadChildren: () => import('./vendor/vendor.module').then(m => m.GnsgapplicationVendorModule),
      },
      {
        path: 'revenue',
        loadChildren: () => import('./revenue/revenue.module').then(m => m.GnsgapplicationRevenueModule),
      },
      {
        path: 'revenue-report',
        loadChildren: () => import('./revenue-report/revenue-report.module').then(m => m.GnsgapplicationRevenueReportModule),
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.GnsgapplicationTaskModule),
      },
      {
        path: 'sevadar',
        loadChildren: () => import('./sevadar/sevadar.module').then(m => m.GnsgapplicationSevadarModule),
      },
      {
        path: 'p-roul',
        loadChildren: () => import('./p-roul/p-roul.module').then(m => m.GnsgapplicationPRoulModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class GnsgapplicationEntityModule {}
