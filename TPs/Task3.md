# Task 3: Understanding Dependency Injection and Implementing Add Report Feature

## Dependency Injection with Dagger Hilt

The Imatah app now uses Dagger Hilt for dependency injection across all layers of the application. Here's how it's structured:

### 1. Data Layer
- Repository implementations are provided via Hilt modules
- Data sources and their dependencies are injected into repositories
- Example: `ReportRepository` and `CategoryRepository` are injected with their dependencies

### 2. Domain Layer
- Use cases are injected with their required repositories
- Examples:
  - `GetReportsUseCase` is injected with `ReportRepository`
  - `AddReportUseCase` is injected with `ReportRepository`
  - `GetCategoriesUseCase` is injected with `CategoryRepository`

### 3. Presentation Layer
- ViewModels are annotated with `@HiltViewModel`
- Use cases are injected into ViewModels
- Example: `ReportViewModel` is injected with `GetReportsUseCase`

## Your Task: Implement Add Report Feature

### 1. Create Add Report View
Create a new composable function `AddReportScreen` in the presentation layer with the following requirements:

- Input fields for:
  - Report title
  - Report description
  - Category selection (dropdown)
  - Location coordinates
  - Image URL
  - Status (New, In Progress, Fixed)
- Validation for all fields
- Submit button
- Success/Error feedback

### 2. Enhance AddReportViewModel

Update the `AddReportViewModel` to include:

```kotlin
// State for form fields
data class AddReportState(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val coordinates: Pair<Double, Double> = Pair(0.0, 0.0),
    val imageUrl: String = "",
    val status: String = "New",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

// Events that can be triggered
sealed class AddReportEvent {
    data class TitleChanged(val title: String) : AddReportEvent()
    data class DescriptionChanged(val description: String) : AddReportEvent()
    data class CategorySelected(val category: String) : AddReportEvent()
    data class CoordinatesChanged(val latitude: Double, val longitude: Double) : AddReportEvent()
    data class ImageUrlChanged(val url: String) : AddReportEvent()
    data class StatusChanged(val status: String) : AddReportEvent()
    object Submit : AddReportEvent()
}
```

Required functionality:
1. State management for form fields
2. Input validation
3. Error handling
4. Integration with `AddReportUseCase`
5. Success/failure feedback

### Implementation Steps

1. **AddReportViewModel Implementation**
   - Add state management using `StateFlow`
   - Implement event handling
   - Add validation logic
   - Integrate with `AddReportUseCase`

2. **AddReportScreen Implementation**
   - Create UI components using Jetpack Compose
   - Connect UI events to ViewModel
   - Show loading state
   - Display error messages
   - Show success confirmation

3. **Navigation Integration**
   - Add navigation to Add Report screen (integrate with "Add damaged road" button)
   - Handle back navigation
   - Pass results back to previous screen

### Success Criteria

Your implementation should:
1. Successfully add new reports to the repository
2. Validate all input fields
3. Show appropriate loading states
4. Handle and display errors
5. Provide success feedback
6. Follow MVVM architecture patterns
7. Properly utilize dependency injection

## Tips
- Use `viewModelScope` for coroutines in ViewModel
- Implement proper error handling
- Follow Material Design guidelines for the UI
- Use proper state management
- Make use of Hilt's dependency injection
- Consider using location services for coordinates

## Bonus Challenges
1. Add image upload functionality instead of URL
2. Implement form state persistence
3. Add unit tests for ViewModel
4. Add UI tests for the Add Report screen
5. Add map integration for selecting coordinates
6. Implement real-time validation

## Add Report UI Design

The Add Report screen should follow the app's existing design language:
- Use the dark theme consistent with the main screen
- Use consistent typography and spacing
- Follow the existing card and input field styles
- Match the golden accent color (#FFD700) for important elements

The UI should maintain consistency with the existing screens while providing a clear and intuitive interface for adding new reports.

Good luck with implementing the Add Report feature!
