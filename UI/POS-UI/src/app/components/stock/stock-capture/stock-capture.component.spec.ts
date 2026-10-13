import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockCaptureComponent } from './stock-capture.component';

describe('StockCaptureComponent', () => {
  let component: StockCaptureComponent;
  let fixture: ComponentFixture<StockCaptureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockCaptureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockCaptureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
