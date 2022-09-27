// Handle request on button click to add new cake
const button = document.getElementById("addCakeBtn");
button.addEventListener("click", async _ => {
    data = {
        "title": getValue("titleInput"),
        "description": getValue("descriptionInput"),
        "image": getValue("imageInput")
    }
    fetch("http://localhost:8080/cakes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": getCookie("XSRF-TOKEN")
            },
            body: JSON.stringify(data)
        })
        .then(response => handlePostResponse(response))
        .catch(error => console.error("Failed to retrieve cakes:", error));
});

// Call API and load in cakes
function loadCakes() {
    fetch("http://localhost:8080/cakes")
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to retrieve cakes");
            }
        })
        .then(data => displayCakes(data))
        .catch(error => console.error("Failed to retrieve cakes:", error));
}

// Add cakes to div
function displayCakes(data) {
    for (const cake of data) {
        const cakesDiv = document.getElementById("cakes");

        const cakeName = cake.title;
        const heading = document.createElement("h3");
        heading.innerHTML = cakeName;
        cakesDiv.appendChild(heading);

        const cakeDescription = cake.description;
        const paragraph = document.createElement("p");
        paragraph.innerHTML = cakeDescription;
        cakesDiv.appendChild(paragraph);

        const cakeImage = document.createElement("img");
        cakeImage.src = cake.image;
        cakesDiv.appendChild(cakeImage);
    }
}

// If cake failed to add then display error, else refresh page with new cake
function handlePostResponse(response) {
    if (!response.ok) {
        response.text().then(text => alert(text))
    } else {
        document.getElementById("cakeForm").reset();
        window.location.reload()
    }
}

function getValue(inputField) {
    return document.getElementById(inputField).value
}

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}