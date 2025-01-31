fun validatephone(phone: String): String? {
    if (phone.isBlank()) {
        return "Phone number is required"
    }
    if (!phone.matches(Regex("^[+]?[0-9]{8,15}$"))) {
        return "Invalid phone number format"
    }
    return null
}
