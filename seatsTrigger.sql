CREATE TRIGGER seatsTrigger
ON Booking AFTER INSERT, UPDATE, DELETE
AS BEGIN 
	IF(EXISTS(SELECT * FROM deleted))
	BEGIN
		UPDATE Flight_Instance
		SET available_seats = available_seats + total.seatsNum
		FROM Flight_Instance FI, (SELECT flight_code,depart_date,COUNT(passenger_id) AS seatsNum 
								  FROM deleted
								  GROUP BY flight_code, depart_date) AS total
		WHERE FI.flight_code = total.flight_code AND
			  FI.depart_date = total.depart_date
	END 
	
	IF(EXISTS(SELECT * FROM inserted))
	BEGIN
		UPDATE Flight_Instance
		SET available_seats = available_seats - total.seatsNum
		FROM Flight_Instance FI, (SELECT flight_code,depart_date,COUNT(passenger_id) AS seatsNum
								  FROM inserted
								  GROUP BY flight_code, depart_date) AS total
		WHERE FI.flight_code = total.flight_code AND
			  FI.depart_date = total.depart_date
	END
END