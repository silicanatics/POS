import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemsSoldComponent } from './items-sold.component';

describe('ItemsSoldComponent', () => {
  let component: ItemsSoldComponent;
  let fixture: ComponentFixture<ItemsSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ItemsSoldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ItemsSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
