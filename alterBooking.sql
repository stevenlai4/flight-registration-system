delete from Booking;
alter table Flight_Instance add available_seats INTEGER;
update Flight_Instance set available_seats = 10;