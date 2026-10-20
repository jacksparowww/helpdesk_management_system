
const API_URL = "/api/tickets";

const USER_ID = sessionStorage.getItem("userId");
const USER_NAME = sessionStorage.getItem("userName");

// Get HTML elements
const tableBody = document.getElementById("ticket-table-body");
const message = document.getElementById("message");
const createMessage = document.getElementById("create-message");

// Check login and display user name
if (!USER_ID) {
    window.location.href = "/";
} else {
    document.getElementById("user-name").textContent =
        USER_NAME || "HelpDesk User";

    document.querySelector(".avatar").textContent =
        USER_NAME ? USER_NAME.charAt(0).toUpperCase() : "U";

    loadTickets();
}

// Load tickets belonging to the logged-in user
async function loadTickets() {
    message.textContent = "Loading tickets...";
    tableBody.replaceChildren();

    try {
        const response = await fetch(
            `${API_URL}/my?userId=${USER_ID}`
        );

        if (!response.ok) {
            throw new Error("Unable to load tickets.");
        }

        const tickets = await response.json();

        // Update ticket counts
        document.getElementById("total-tickets").textContent =
            tickets.length;

        document.getElementById("open-tickets").textContent =
            tickets.filter(ticket => ticket.status === "OPEN").length;

        document.getElementById("progress-tickets").textContent =
            tickets.filter(
                ticket => ticket.status === "IN_PROGRESS"
            ).length;

        document.getElementById("resolved-tickets").textContent =
            tickets.filter(
                ticket =>
                    ticket.status === "RESOLVED" ||
                    ticket.status === "CLOSED"
            ).length;

        // Display message when no tickets exist
        if (tickets.length === 0) {
            message.textContent =
                "You haven't created any tickets yet.";
            return;
        }

        // Display tickets in the table
        tickets.forEach(ticket => {
            const row = document.createElement("tr");

            const values = [
                ticket.id,
                ticket.title,
                ticket.priority,
                ticket.status,
                ticket.creationDate
                    ? new Date(ticket.creationDate).toLocaleString()
                    : "-"
            ];

            values.forEach(value => {
                const cell = document.createElement("td");
                cell.textContent = value ?? "-";
                row.appendChild(cell);
            });

            tableBody.appendChild(row);
        });

        message.textContent =
            `${tickets.length} ticket(s) loaded.`;

    } catch (error) {
        message.textContent =
            "Could not load tickets. Check that the backend is running.";

        console.error(error);
    }
}

// Refresh tickets
document.getElementById("refresh-button")
    .addEventListener("click", loadTickets);

// Create a new ticket
document.getElementById("ticket-form")
    .addEventListener("submit", async function (event) {
        event.preventDefault();

        createMessage.textContent = "Submitting ticket...";

        const ticket = {
            title: document.getElementById("title").value.trim(),
            description: document.getElementById("description").value.trim(),
            priority: document.getElementById("priority").value
        };

        if (!ticket.title || !ticket.description || !ticket.priority) {
            createMessage.textContent =
                "Please fill in all the fields.";
            return;
        }

        if (!USER_ID) {
            createMessage.textContent =
                "Please log in before creating a ticket.";
            return;
        }

        try {
            const response = await fetch(
                `${API_URL}?userId=${USER_ID}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(ticket)
                }
            );

            if (!response.ok) {
                const errorText = await response.text();

                throw new Error(
                    errorText || "Ticket creation failed."
                );
            }

            createMessage.textContent =
                "Ticket created successfully!";

            document.getElementById("ticket-form").reset();

            await loadTickets();

        } catch (error) {
            createMessage.textContent =
                error.message || "Could not create the ticket.";

            console.error(error);
        }
    });
