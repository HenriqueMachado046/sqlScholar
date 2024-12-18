// Gráfico pizza = Acertos vs Questões cadastradas;
// Gráfico linha = Erros vs Questões cadastradas;
// Gráfico barra = Questoes cadastradas (professor x) vs Questoes cadastradas (geral);

function comparar(a, b) {
    return a-b;    
}


function createChart(xValues, yValues) {

    if (document.getElementById("myChart") === null) {
        const newParagraph = document.createTextNode("Grafico de erros por questoes cadastradas");
        const newDiv = document.createElement("div", "chart1")
        const newCanvas = document.createElement("canvas", "myChart")
        newDiv.setAttribute("id", "chart1")
        newDiv.setAttribute("class", "float-child")
        newDiv.appendChild(newParagraph);
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
                label: 'Erros totais dos alunos',
                fill: false,
                lineTension: 0,
                backgroundColor: "rgba(0,0,255,1.0)",
                borderColor: "rgba(0,0,255,0.1)",
                data: [yValues]
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

function createBarChart(xValues, countTotal) {

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

    const data = {
        labels: ['Cadastradas Professor', 'Cadastradas totais'],
        datasets: [{
            label: "Grafico de contribuicao",
            data: [ parseInt(xValues), parseInt(countTotal)],
            backgroundColor: 'rgb (255, 99, 126)',
            borderColor: 'rgb (255, 99, 132)',
            borderWidth: 1
        }]
    }

    new Chart(document.getElementById('myChart2'), {
        type: 'bar',
        data: data ,
        options:{
            responsive: true,
            scales:{
                beginAtZero: true,
            },
            legend:{
                position: 'top',
            },
            title:{
                display:true,
                text: 'Grafico de contribuicao'
            }
        }
    })         
};

function createPieChart(xValues, zValues) {

    const dataPie = {
        labels: ['Questoes cadastradas', 'Questoes resolvidas'],
        datasets: [
            {
                label: 'Acertos por questões cadastradas',
                data: [xValues, zValues],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(54, 162, 235)',
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
                text: 'Acertos por questões cadastradas'
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