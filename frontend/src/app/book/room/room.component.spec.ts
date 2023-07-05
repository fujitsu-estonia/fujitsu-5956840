import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomComponent } from './room.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';

describe('RoomComponent', () => {
  let component: RoomComponent;
  let fixture: ComponentFixture<RoomComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
      declarations: [RoomComponent]
    });
    fixture = TestBed.createComponent(RoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
