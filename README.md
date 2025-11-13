# Mcq Quiz - Android (Jetpack Compose)

## What this delivers
- A minimal Jetpack Compose Android app implementing the assignment:
  - Parse questions from `app/src/main/assets/questions.json`
  - Question screen with 4 options, reveal correct/selected answer
  - Auto-advance after 2 seconds on answer reveal
  - Skip button to immediately go to next question
  - Streak logic: consecutive correct answers tracked; streak badge lights at 3
  - Results screen with correct/total, skipped, longest streak, Restart button
- Project skeleton you can open in Android Studio (Electric/Electric variants).
- Implementation focuses on clarity and separation (ViewModel for state).

## How to run
1. Open in Android Studio.
2. If needed, update the Gradle wrapper in Android Studio (Open Gradle tool window -> Use default wrapper).
3. Run the app on an emulator or device.

## Notes
- This is a minimal functional example for the assignment. You can extend animations,
  network loading (fetching the gist), and polish per your taste.
