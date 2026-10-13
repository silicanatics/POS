import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountantReportComponent } from './accountant-report.component';

describe('AccountantReportComponent', () => {
  let component: AccountantReportComponent;
  let fixture: ComponentFixture<AccountantReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountantReportComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountantReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
