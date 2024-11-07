function comparar(a, b) {
    return a-b;    
}


function createChart(xValues, yValues) {
    if (document.getElementById("myChart") === null) {
        const newDiv = document.createElement("div", "chart1")
        const newCanvas = document.createElement("canvas", "myChart")
        newDiv.setAttribute("id", "chart1")
        newDiv.setAttribute("class", "float-child")
        newCanvas.setAttribute("id", "myChart")
        newCanvas.setAttribute("style", "width:100%;max-width:600px")
        document.getElementById("canvasDiv").insertAdjacentElement("afterbegin", newDiv)
        document.getElementById("chart1").insertAdjacentElement("afterbegin", newCanvas)
    }
    new Chart(document.getElementById('myChart').id, {
        type: "line",
        data: {
            labels: xValues,
            datasets: [{
                label: 'Grafico linha teste',
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
                myScale: {
                    type: 'logarithmic',
                    position: 'right'
                }
            }
        }
    });
};

function createBarChart(xValues) {

    if (document.getElementById("myChart2") === null) {
        const newDiv = document.createElement("div", "chart2")
        const newCanvas = document.createElement("canvas", "myChart2")
        newCanvas.setAttribute("id", "myChart2")
        newCanvas.setAttribute("style", "width:100%;max-width:600px")
        newDiv.setAttribute("class", "float-child")
        newDiv.setAttribute("id", "chart2")
        document.getElementById("canvasDiv").insertAdjacentElement("afterbegin", newDiv)
        document.getElementById("chart2").insertAdjacentElement("afterbegin", newCanvas)
    }

    new Chart(document.getElementById('myChart2'), {
        type: 'bar',
        data:{
            labels: ['Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio'],
            datasets: [{
                label: "Grafico barra do primeiro semestre do ano",
                backgroundColor: 'rgb (255, 99, 126)',
                borderColor: 'rgb (255, 99, 132)',
                data: xValues
            }]
        },
        options:{
            responsive: true,
            legend:{
                position: 'top',
            },
            title:{
                display:true,
                text: 'Exemplo barra'
            }
        }
    })         
};

function createPieChart(xValues) {

    const dataPie = {
        labels: ['Area 1', 'Area 2', 'Area 3', 'Area 4', 'Area 5'],
        datasets: [
            {
                label: 'Dataset 1',
                data: xValues,
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
                    'rgb(255, 205, 86)',
                    'rgb(115, 225, 19)'
                ],
                hoverOffset: 4
            }
        ]
    };

    if (document.getElementById("myChart3") === null) {
        const newDiv = document.createElement("div", "chart3")
        const newCanvas = document.createElement("canvas", "myChart3")
        newCanvas.setAttribute("id", "myChart3")
        newCanvas.setAttribute("style", "width:100%;max-width:900px")
        newDiv.setAttribute("class", "float-child")
        newDiv.setAttribute("id", "chart3")
        document.getElementById("canvasDiv").insertAdjacentElement("afterbegin", newDiv)
        document.getElementById("chart3").insertAdjacentElement("afterbegin", newCanvas)
    }

    new Chart(document.getElementById('myChart3'), {
        type: 'pie',
        data: dataPie,
        options:{
            responsive: true,
            legend:{
                position: 'top',
            },
            title:{
                display:true,
                text: 'Exemplo pizza'
            }
        }
    })
};

function showChart() {
    const toggle = document.getElementById('canvasDiv')
    if (toggle.style.display === "none") {
        toggle.style.display = "block";
    } else {
        toggle.style.display = "none"
    }
}  