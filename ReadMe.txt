Note: Make sure to add both .jar files (mssql-jdbc-6.2.1.jre8 & rs2xml) to the library.
      The .jar files are in the folder JAR

1. Assignment7GUI:
   -->This is the source file that you need to execute.
   -->After you execute this file, a JFrame will pop up and there is a JMenu with "Options" on it.
   -->Click "Options"
   -->Then you can see a list of options: Insert New Passenger, View Passenger Flight Instance, and Add Booking Record.
   -->Choose one and a JFrame will pop up.

2. InsertNewPassenger:
   -->After InsertNewPassenger's JFrame pop up, you'll see the JTextfields for first name and last name.
   -->Input first name and last name.
   -->Click "Submit" after you input the first name and last name.

3. ViewPassengerFlightInstance:
   -->After ViewPassengerFlightInstance's JFrame pop up, you'll see the JTextfields for flight code and departure date.
   -->Input flight code and departure date.
   -->Click "Search" after you input the flight code and departure date.
   -->After you clicked "Search", you see the table shows up on the right square if there is a booking for
      the flight code and departure date.
   -->You can also see the available seats below the square box.

4. AddBookingRecord:
   -->After AddBookingRecord's JFrame pop up, you'll see JTextfields for passenger id, flight code 1, departure date 1,
      flight code 2, departure date 2. However, flight code 2 and departure date 2 are disabled because I set single trip
      as default.
   -->If you want to add multi-city trip then you can click the button "Multi-City Trip" then flight code 2
      and depature date 2 will be enabled. (You can also go back to single trip by clicking "Single Trip" button)
   -->After you fill in the JTextfields, you can then click the "Insert Booking" button.