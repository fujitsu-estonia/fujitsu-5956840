package ee.fujitsu.smit.hotel.services;

import ee.fujitsu.smit.hotel.constants.BookingStatus;
import ee.fujitsu.smit.hotel.exceptions.BookingNotCancelledException;
import ee.fujitsu.smit.hotel.exceptions.NotFoundException;
import ee.fujitsu.smit.hotel.models.BookingDto;
import ee.fujitsu.smit.hotel.repositories.BookingRepository;
import ee.fujitsu.smit.hotel.tools.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

  private final BookingRepository bookingRepository;
  private final BookingMapper bookingMapper;

  public UUID createBooking(BookingDto bookingDto) {
    var entity = bookingMapper.mapToEntity(bookingDto);
    entity.setStatus(BookingStatus.REQUESTED);

    var saved = bookingRepository.saveAndFlush(entity);
    log.debug("Booking {} saved. Id: {}", bookingDto, saved.getId());

    return saved.getId();
  }

  public void cancelBooking(UUID bookingId, boolean cancelledByUser) {
    var entity = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
    entity.setStatus(
        cancelledByUser ? BookingStatus.CANCELLED_BY_USER : BookingStatus.CANCELLED_BY_ADMIN);

    try {
      bookingRepository.saveAndFlush(entity);
    } catch (Exception ex) {
      log.debug("Couldn't cancel booking", ex);
      throw new BookingNotCancelledException();
    }
  }

  public BookingDto getBooking(UUID bookingId) {
    return bookingRepository
        .findById(bookingId)
        .map(bookingMapper::mapToDto)
        .orElseThrow(NotFoundException::new);
  }
}
