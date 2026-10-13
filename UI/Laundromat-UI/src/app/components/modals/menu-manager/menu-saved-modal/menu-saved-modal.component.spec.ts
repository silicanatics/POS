import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MenuSavedModalComponent } from './menu-saved-modal.component';

describe('MenuSavedModalComponent', () => {
  let component: MenuSavedModalComponent;
  let fixture: ComponentFixture<MenuSavedModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MenuSavedModalComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuSavedModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
