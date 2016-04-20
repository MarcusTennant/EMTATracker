EMTATracker
===========

EMTATracker is a gps tracker that utilizes the 3g network to enable municiple vehicle tracking in virtually any city.
Developed as a prototype for the Erie Metro Transport Authority by Jeff Knapp, Matt Smith and Marcus Tennant.

The backend consists of a java program controlling a mail server using a google api.
The front end pulls the data provided by the mail server and makes use of the google maps api to display the tracking data in real time.

Designed with three goals in mind (ability to be used virtually anywhere in the US, robustness, and ease of installation), the only hardware needed is an Arduino with gps and 3g module, and a 5v power connection on whatever bus, subway, etc, that you're tracking.

The front end is designed for both desktop and mobile in Javascript and HTML5 so no app installation is needed. 

Also provided in this repo is a test loop [demoWebpage.html](./demoWebpage.html) of gps data pulled from our arduino of a drive around Penn State Behrend. As well as the actual [webpage](./webpage.html) which pulled live bus data from the server.

The project was showcased at an Erie County Metropolitan Planning Committee Meeting, as well as to members of EMTA and the Pennsylvania DOT in August 2013.

Mobile View            
![screenshot](./AndroidTest.jpg)

Desktop View
![screenshot](./DesktopTest.jpg)
