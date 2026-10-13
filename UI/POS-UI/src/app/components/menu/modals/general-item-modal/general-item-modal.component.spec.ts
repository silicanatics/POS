import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneralItemModalComponent } from './general-item-modal.component';

describe('GeneralItemModalComponent', () => {
  let component: GeneralItemModalComponent;
  let fixture: ComponentFixture<GeneralItemModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GeneralItemModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneralItemModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
