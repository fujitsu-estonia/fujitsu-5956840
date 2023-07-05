import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomSearchComponent } from './room-search.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';

describe('RoomSearchComponent', () => {
  let component: RoomSearchComponent;
  let fixture: ComponentFixture<RoomSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
      declarations: [RoomSearchComponent]
    });
    fixture = TestBed.createComponent(RoomSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
