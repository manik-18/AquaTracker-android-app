<div align="center">
  <img src="https://img.shields.io/badge/Made%20with-Java%20%7C%20XML%20%7C%20Firebase-blue.svg" alt="Made with Java | XML | Firebase">
</div>

# 🐠 AquaTracker Android App

AquaTracker is a mobile application designed to monitor and display real-time aquarium parameters such as pH, hardness, temperature, and dissolved oxygen. With Aqua Track, users can effortlessly track the health of their aquariums from anywhere with internet access.

## 🚀 Features
- Real-time monitoring of aquarium parameters.
- User-friendly interface with intuitive chart visualizations.

## 📱 Screenshots

<img src="https://github.com/manik-18/AquaTracker-android-app/assets/102967918/653a96e9-499e-4ff7-8af1-9a38be14376a" width="270" height="600">

<img src="https://github.com/manik-18/AquaTracker-android-app/assets/102967918/3043419b-9009-4636-8e90-03bef8dff03f" width="270" height="600">

<img src="https://github.com/manik-18/AquaTracker-android-app/assets/102967918/71d447de-5d24-4a69-90af-a14bf86c972c" width="270" height="600">

<img src="https://github.com/manik-18/AquaTracker-android-app/assets/102967918/2ac5e19f-f956-4167-b484-7a9c4e51ce27" width="270" height="600">

<img src="https://github.com/manik-18/AquaTracker-android-app/assets/102967918/30517b6e-c568-48cd-9e81-2a80d57a21d7" width="270" height="600">


## 🛠️ Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/manik-18/AquaTracker-android-app.git
   ```

2. Open the project in Android Studio.

3. Add your `google-services.json` file to the `app/` directory.

4. Build and run the project on your Android device or emulator.

## 🛠️ Technologies Used
- Java
- XML
- Firebase

## 🔥 Firebase Setup
1. Create a Firebase project at Firebase Console.

2. Download the `google-services.json` file from the Firebase console.

3. Copy the `google-services.json` file to the `app/` directory of your Android project.

4. Enable Email/Password authentication:
   - Go to the Firebase Console and select your project.
   - Navigate to the "Authentication" section from the left-hand menu.
   - Click on the "Sign-in method" tab.
   - Enable the "Email/Password" sign-in provider.

5. Turn on Realtime Database from the left-hand menu.

## Sample Data for Realtime Database

You can use the following sample data structure to populate your Realtime Database with mock aquarium parameter readings:

```json
{
  "pHdata": {
    "min": 6.5,
    "max": 7,
    "values": [
      {
        "time": "2024-01-24T12:15:00",
        "value": 7.2
      },
      {
        "time": "2024-01-24T12:30:00",
        "value": 8.1
      },
      ...
    ]
  },
  "temperature": {
    "min": 29,
    "max": 31,
    "values": [
      {
        "time": "2024-01-24T12:00:00",
        "value": 30
      },
      ...
    ]
  },
  "oxygen_levels": {
    "min": 5,
    "max": 7,
    "values": [
      {
        "time": "2024-01-24T12:00:00",
        "value": 6
      },
      ...
    ]
  },
  "hardness": {
    "min": 70,
    "max": 140,
    "values": [
      {
        "time": "2024-01-24T12:00:00",
        "value": 80
      },
      ...
    ]
  }
}
```

## 🚨 Parameter Range Notification
If any parameter deviates from the normal range, users will receive a notification to ensure timely action is taken to maintain the health of their aquarium.

## 🤝 Contribution
Contributions are welcome! Please fork the repository and create a pull request with your suggested changes.
