import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VeeraErrorComponent } from './veera-error.component';

describe('VeeraErrorComponent', () => {
  let component: VeeraErrorComponent;
  let fixture: ComponentFixture<VeeraErrorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(VeeraErrorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
