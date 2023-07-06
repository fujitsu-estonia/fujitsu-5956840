import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
      declarations: [HeaderComponent]
    });
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit sidenavToggle event when toggleSidenav is called', () => {
    spyOn(component.sidenavToggle, 'emit');
    component.toggleSidenav();
    expect(component.sidenavToggle.emit).toHaveBeenCalled();
  });
});
