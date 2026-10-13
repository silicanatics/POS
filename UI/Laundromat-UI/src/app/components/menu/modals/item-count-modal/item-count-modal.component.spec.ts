import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemCountModalComponent } from './item-count-modal.component';

describe('ItemCountModalComponent', () => {
  let component: ItemCountModalComponent;
  let fixture: ComponentFixture<ItemCountModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemCountModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemCountModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
