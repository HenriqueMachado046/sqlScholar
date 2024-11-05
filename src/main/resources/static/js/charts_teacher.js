const xValues = [5, 8, 1, 2, 4, 7, 5, 13, 11, 12, 17];
const yValues = [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15];
function createChart() {

    if (document.getElementById("myChart") === null) {
        const newCanvas = document.createElement("canvas", "myChart")
        newCanvas.setAttribute("id", "myChart")
        newCanvas.setAttribute("style", "width:100%;max-width:600px")
        const position = document.getElementById("canvasDiv")
        document.getElementById("canvasDiv").insertAdjacentElement("afterbegin", newCanvas)
    }
    new Chart(document.getElementById('myChart').id, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                fill: false,
                lineTension: 0,
                backgroundColor: "rgba(0,0,255,1.0)",
                borderColor: "rgba(0,0,255,0.1)",
                data: yValues
            }]
        },
        options: {
            legend: { display: false },
            scales: {
                yAxes: [{ ticks: { min: 6, max: 16 } }],
            }
        }
    });
}

function showChart() {
    const toggle = document.getElementById('myChart')
    if (toggle.style.display === "none") {
        toggle.style.display = "block";
    } else {
        toggle.style.display = "none"
    }
}  