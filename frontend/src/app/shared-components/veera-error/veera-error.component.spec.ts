import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VeeraErrorComponent } from './veera-error.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';

describe('VeeraErrorComponent', () => {
  let component: VeeraErrorComponent;
  let fixture: ComponentFixture<VeeraErrorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
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
