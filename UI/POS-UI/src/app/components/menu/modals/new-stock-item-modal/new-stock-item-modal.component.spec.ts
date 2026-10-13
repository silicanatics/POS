import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewStockItemModalComponent } from './new-stock-item-modal.component';

describe('NewStockItemModalComponent', () => {
  let component: NewStockItemModalComponent;
  let fixture: ComponentFixture<NewStockItemModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewStockItemModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewStockItemModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
