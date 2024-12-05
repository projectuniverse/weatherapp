# Weather and Environmental Sensor App

## An app that displays the user's current weather and the device's environmental sensor data
This project was the first Android app I worked on, and it allows users to see the weather at their current location, including the outdoor temperature, weather type, and wind speed. Additionally, it displays the device's environmental sensor data. This includes the ambient air temperature, illuminance, ambient air pressure, and ambient relative humidity.

The app can be used in any country, as it supports both the metric and imperial systems. Users can switch between the displayed units by clicking in the upper-right corner and selecting either "Metric" or "Imperial." This affects the units for temperature and wind speed. In the metric system, temperature is displayed in Celsius and wind speed in km/h. In the imperial system, temperature is displayed in Fahrenheit and wind speed in mph.

## Screenshots
<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://github.com/user-attachments/assets/ec1bf692-bbdf-4755-a1ac-50cfa06a695b" width="300">
  <source media="(prefers-color-scheme: light)" srcset="https://github.com/user-attachments/assets/39069deb-8ddf-4b7d-8c95-45163dd5b03b" width="300">
  <img alt="The app's weather screen using the metric system." src="https://github.com/user-attachments/assets/ec1bf692-bbdf-4755-a1ac-50cfa06a695b" width="300">
</picture>
&nbsp;
<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://github.com/user-attachments/assets/bca5e572-1eed-4343-84f1-71b24aeaf78f" width="300">
  <source media="(prefers-color-scheme: light)" srcset="https://github.com/user-attachments/assets/2b174d8b-de64-411b-a565-e21e6faf0d36" width="300">
  <img alt="The app's environmental sensors screen using the metric system." src="https://github.com/user-attachments/assets/bca5e572-1eed-4343-84f1-71b24aeaf78f" width="300">
</picture>

<br>
<br>

**Note:** Whether you see the light mode or dark mode screenshots of the app depends on your device's theme.

## How to install and run this app on your Android device
1. Install Android Studio and `clone` this project
2. Generate an OpenWeather API key for current weather
3. In the project's root directory that also contains `local.defaults.properties`, create a file called `secrets.properties`
4. Paste the following line of code into `secrets.properties` and replace `YOUR_API_KEY` with your own API key: `OPEN_WEATHER_API_KEY=YOUR_API_KEY`
5. Build the APK
6. Install the APK on your Android device
