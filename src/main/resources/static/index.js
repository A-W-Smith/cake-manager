fetch("http://localhost:8080/cakes")
    .then((response) => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Failed to retrieve cakes");
        }
    })
    .then(data => {
        console.log(data);
        displayCakes(data)
    })
    .catch((error) => console.error("Failed to retrieve cakes:", error));

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