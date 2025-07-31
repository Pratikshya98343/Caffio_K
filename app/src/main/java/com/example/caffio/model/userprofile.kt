// UserProfile.kt
data class UserProfile(
    val fullName: String = "",
    val email: String = "",
    val preferences: List<String> = emptyList()
)