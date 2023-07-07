package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.enums.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.BookingAlreadyCancelledException;
import ee.fujitsu.smit.hotel.exceptions.BookingNotCancelledException;
import ee.fujitsu.smit.hotel.exceptions.NoAvailableRoomsException;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.booking.BookingDetailsDto;
import ee.fujitsu.smit.hotel.models.booking.CreateBookingRequestDto;
import ee.fujitsu.smit.hotel.models.booking.SearchBookingsDto;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.tools.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;

  @Transactional
  public UUID createBooking(CreateBookingRequestDto bookingDto) {
    var entity = bookingMapper.mapToEntity(bookingDto);
    if (bookingRepository.countAvailableRoomsOfTypeForPeriod(entity) < 1) {
      throw new NoAvailableRoomsException(entity.getRoomType().getTitle());
    }

    entity.setStatus(BookingStatus.ACCEPTED);
    var saved = bookingRepository.saveAndFlush(entity);
    log.debug("Booking {} saved. Id: {}", bookingDto, saved.getId());

    return saved.getId();
  }

  public void cancelBooking(UUID bookingId, boolean cancelledByUser) {
    var entity = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
    if (entity.getStatus().isCancelled()) {
      throw new BookingAlreadyCancelledException();
    }

    try {
      entity.setStatus(
          cancelledByUser ? BookingStatus.CANCELLED_BY_USER : BookingStatus.CANCELLED_BY_ADMIN);
      bookingRepository.saveAndFlush(entity);
    } catch (Exception ex) {
      log.debug("Couldn't cancel booking", ex);
      throw new BookingNotCancelledException();
    }
  }

  public BookingDetailsDto getBooking(UUID bookingId) {
    return bookingRepository
        .findById(bookingId)
        .map(bookingMapper::mapToDto)
        .orElseThrow(NotFoundException::new);
  }

  public List<BookingDetailsDto> findBookings(SearchBookingsDto searchBookings) {
    return bookingRepository.findByStatusAndTimeBounds(searchBookings).stream()
        .map(bookingMapper::mapToDto)
        .toList();
  }
}
