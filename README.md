# Major Project "Automated Guided Vehicle"
Created by me and 3 other students

## Project Description
- We were given a robot. The robot consisted of servo motors (wheels), a raspberry pi running custom software to act as an Arduino, a motor controller, sensors (ir, ultrasone, bluetooth) and neopixels.
- Context: The purpose of this robot was to drive in a restaurant. It had to pickup carts with plates and glasses. We had to make sure it would not bump into anyone. It had to be remote controlled via a program on the computer, and via a tv remote.
- The user interface. We made a user interface using 2D Graphics, so that the user was able to connect with the robot and draw a path where the robot should go. You would also be able to define the position of the kitchen, tables and carts.
- The robot. We used the neopixels to indicate the status of the robot: where its going, if its stuck, if it made an emergency stop, etc. It was able to be remote controlled via the tv remote. It would also be able to be controlled via the program running on the pc. We used servo motors to let it drive, and also for a gripper to pick up carts. The ultrasone sensor made sure it would not bump into anything.
- In the code we made sure to use callbacks, so that we would not have to check the status of the sensors, but the sensors would notify the other code (e.g. an obstackle in front of the robot).

## Grade
6.9 (nice) / 10

## Date
Year 1, Period 2 | April 12, 2023 12:00pm
