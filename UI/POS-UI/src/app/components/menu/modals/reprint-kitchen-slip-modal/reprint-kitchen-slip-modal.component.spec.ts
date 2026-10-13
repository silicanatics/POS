import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReprintKitchenSlipModalComponent } from './reprint-kitchen-slip-modal.component';

describe('ReprintKitchenSlipModalComponent', () => {
  let component: ReprintKitchenSlipModalComponent;
  let fixture: ComponentFixture<ReprintKitchenSlipModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReprintKitchenSlipModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ReprintKitchenSlipModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
