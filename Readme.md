# Android App - iHealth BG5 Device
This application lets the user takes its Blood Sugar reading using [iHealth BG5](https://ihealthlabs.com/glucometer/wireless-smart-gluco-monitoring-system/) device.
Some of the features of the apps:
- Integrated iHealth labs libraries for all of their bluetooth devices (currently hardcoded for BG5 but can be changed)
- Includes the messaging functionality using [FIREBASE](https://firebase.google.com/)
- Connection to MySQL DB
- Login using PHP script [AuthO](https://auth0.com/) is preferred and has to be implemented
- History Fragment to view all the previous blood sugar reading

##Getting Started
Download the project and import it in [Android Studio](https://developer.android.com/studio/index.html?gclid=Cj0KEQiA4o3DBRCJsZqh8vWqt_8BEiQA2Fw0eW_bnT6yeQz3NNsXvtiMwD6jibXbkLDZUkCJ7YYGHu0aApSB8P8HAQ).
Update your android studio to atleast v2.2.2 .

##Prerequisites
- Android studio v2.2.2 or above
- Target Android SDK Version: 23 i.e. Android Marshmallow (6.0)
- Make sure you are connected to wifi or mobile data (everything is retrieved from MySQL DB)
- Give all the permissions such as enable bluetooth, service location, local storage when connecting to BG5 Device


##Screenshots
![](https://cloud.githubusercontent.com/assets/12661418/21534491/e7193cdc-cd35-11e6-99e1-fd392376a221.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534485/e70f7f30-cd35-11e6-9148-640f50d15030.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534490/e710bddc-cd35-11e6-86f2-19af670c1e6c.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534486/e70fb360-cd35-11e6-8276-829996ba34be.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534488/e70fc850-cd35-11e6-880d-0c58a720069a.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534487/e70fab4a-cd35-11e6-8f21-d03d4952f615.png)
![](https://cloud.githubusercontent.com/assets/12661418/21534489/e70fe1be-cd35-11e6-950b-0ec9f72bba34.jpeg)
