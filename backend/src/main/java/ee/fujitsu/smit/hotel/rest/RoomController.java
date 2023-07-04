package ee.fujitsu.smit.hotel.rest;

import ee.fujitsu.smit.hotel.model.Room;
import ee.fujitsu.smit.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getAllRooms(@RequestParam(required = false) Integer roomNumber) {
        try {
            List<Room> rooms = new ArrayList<>();

            if (roomNumber == null)
                rooms.addAll(roomService.findAll());
            else
                rooms.addAll(roomService.findByRoomNumber(roomNumber));

            if (rooms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(rooms, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable("id") long id) {
        Optional<Room> roomData = roomService.findById(id);

        return roomData.map(room -> new ResponseEntity<>(room, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/rooms")
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        try {
            Room _room = roomService
                    .save(new Room(room.getRoomNumber(), room.getBeds(), room.getDescription()));
            return new ResponseEntity<>(_room, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
