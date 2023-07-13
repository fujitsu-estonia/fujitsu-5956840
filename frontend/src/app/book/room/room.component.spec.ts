import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Room, RoomComponent } from './room.component';
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
    component.booking = {}
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit the bookRoomPressed event when bookRoom is called', () => {
    const room: Room = {
      title: 'Test Room',
      bedsCount: 2,
      pricePerNight: 100,
      freeRooms: 5,
    };
    let emittedRoom: Room | undefined;
    component.bookRoomPressed.subscribe((room) => (emittedRoom = room));

    component.bookRoom(room);

    expect(emittedRoom).toEqual(room);
  });
});
