package ee.fujitsu.smit.hotel.services.impl;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.exceptions.booking.BookingAlreadyCancelledException;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.services.BookingService;
import ee.fujitsu.smit.hotel.tools.mappers.BookingMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;
  private final BookingRoomAssigner roomAssigner;

  @Override
  @Transactional
  public UUID createBooking(CreateBookingRequestDto createBookingRequestDto) {
    var entity = bookingMapper.mapToEntity(createBookingRequestDto);
    entity.setStatus(BookingStatus.ACCEPTED);

    roomAssigner.assignAvailableRoom(entity);

    var saved = bookingRepository.saveAndFlush(entity);
    log.debug("Booking {} saved. Id: {}", createBookingRequestDto, saved.getId());

    return saved.getId();
  }

  @Override
  @Transactional
  public boolean cancelBooking(UUID bookingId, boolean cancelAsUser) {
    var entity = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
    if (entity.getStatus().isCancelled()) {
      throw new BookingAlreadyCancelledException();
    }
    entity.setStatusCancelled(cancelAsUser);
    bookingRepository.saveAndFlush(entity);
    return true;
  }

  @Override
  public BookingDetailsDto getBooking(UUID bookingId) {
    return bookingRepository
        .findById(bookingId)
        .map(bookingMapper::mapToDto)
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public @NonNull List<BookingDetailsDto> findBookings(@NonNull SearchBookingsDto searchBookings) {
    return bookingRepository.findByStatusAndTimeBounds(searchBookings).stream()
        .map(bookingMapper::mapToDto)
        .toList();
  }
}
