
const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");
const loginButton = document.getElementById("loginButton");

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    message.textContent = "";
    message.className = "message";

    loginButton.disabled = true;
    loginButton.textContent = "Signing in...";

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        const result = await response.json().catch(() => null);

        if (!response.ok) {
            throw new Error(
                typeof result === "string"
                    ? result
                    : "Invalid email or password."
            );
        }

        // Show success message
        message.textContent =
            "Login successful! Welcome, " +
            (result.name || "User") + ".";

        message.className = "message success";

        // Save logged-in user details
        sessionStorage.setItem("userId", result.id);
        sessionStorage.setItem("userName", result.name || "User");
        sessionStorage.setItem("userRole", result.role || "");

        // Redirect to dashboard
        window.location.href = "/dashboard";

    } catch (error) {
        message.textContent =
            error.message || "Unable to connect to the server.";

        message.className = "message error";

    } finally {
        loginButton.disabled = false;
        loginButton.textContent = "Sign In";
    }
});
