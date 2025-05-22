# 📱 InnovationProjectGT0105

**InnovationProjectGT0105** is a mobile application developed using **Kotlin** and **Jetpack Compose** that allows users to create, manage, and explore personalized events. This project was built as part of a university assignment and integrates modern Android components such as **Room (SQLite)** for local persistence, **Firebase Authentication** for secure login, and **Google Maps / Places API** for enhanced location-based features.

---

## ✨ Features

- 🔐 User registration and login with Firebase Authentication.
- 📆 Create, edit, and delete custom events.
- ⭐ Mark events as favorites.
- 🖼️ Add an image to represent each event.
- 📍 Select an event location using Google Places API.
- 🗺️ View event location directly in Google Maps.
- 🧠 Clean and maintainable architecture using MVVM + Repository pattern.
- 📦 Offline support using Room and local storage.

---

## 🛠️ Tech Stack

| Layer        | Technology                              |
|-------------|------------------------------------------|
| Language     | Kotlin                                   |
| UI Framework | Jetpack Compose                         |
| Database     | Room (SQLite) + KSP                     |
| Auth         | Firebase Authentication                 |
| Images       | Coil                                     |
| Maps         | Google Places API + Maps Intent         |
| State        | StateFlow + ViewModel                   |
| Storage      | Internal app storage (Uri persistence)  |

---

## 🧱 Project Architecture

This project follows a clean and modular **MVVM architecture**:

```
com.g05.innovationprojectgt01\_05/
├── auth/                  → Login & Register screens
├── data/
│   ├── dao/               → Room DAO interfaces
│   ├── entities/          → Room Entities (EventEntity, UserEntity)
│   └── repository/        → Repositories to abstract data operations
├── ui/
│   ├── screens/           → Add, Edit, View, and Home screen UIs
│   └── viewmodel/         → ViewModels for UI logic and state management
└── MainActivity.kt        → App launcher entry point
```
---

## 🔧 Requirements

- Android Studio Giraffe (or newer)
- Kotlin 2.1.0
- AGP 8.8.2
- Google Maps API key (configured)
- Firebase project with Authentication enabled

---

## 🚀 Getting Started

1. **Clone the repository:**

```bash
git clone https://github.com/your-username/InnovationProjectGT0105.git
````

2. **Open in Android Studio**

3. **Configure Firebase:**

   * Download your `google-services.json` and place it in `/app`.

4. **Configure Maps API Key:**

   * Add your key in `local.properties`:

     ```
     MAPS_API_KEY=your_api_key_here
     ```

5. **Build and run the app on an emulator or physical device**

---

## 🧪 Testing

Testing is not yet implemented but will follow `ViewModel` unit testing and UI testing using Compose Test APIs in future iterations.

---

## 📄 License

This project is for academic purposes and does not currently have a license. Contributions are welcome for learning and personal development use.

---

## 🙌 Acknowledgements

* [Android Developers Documentation](https://developer.android.com/docs)
* [Firebase Authentication](https://firebase.google.com/docs/auth)
* [Google Places API](https://developers.google.com/maps/documentation/places)
* [Jetpack Compose](https://developer.android.com/jetpack/compose)

---

