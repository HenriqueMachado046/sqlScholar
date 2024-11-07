
const xValues = [5, 8, 1, 2, 4, 7, 5, 13, 11, 12, 17];
const yValues = [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15];
const solved = 0;
//Aqui está um momento de escolha. Os gráficos serão definidos previamente ou poderão ser criados?
//Gráfico de questões acertadas por este aluno;
//3 indicadores estatísticos para aluno e professores;
//2 processos de gamificação;
//1 gráfico pizza, 1 Gráfico Linha, 1 Gráfico barra; OK

//Tabela

//Dentro desta função deverá ser passado de parâmetro o que é necessário para criar os gráficos de maneira dinâmica.
function createChart() {
    if (document.getElementById("myChart") === null) {
        const newDiv = document.createElement("div", "chart1")
        const newCanvas = document.createElement("canvas", "myChart")
        newCanvas.setAttribute("id", "myChart")
        newCanvas.setAttribute("style", "width:100%;max-width:600px")
        document.getElementById("canvasDiv").insertAdjacentElement("afterbegin", newCanvas)
        document.getElementById("chart1").insertAdjacentElement("afterbegin", newCanvas)

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

function createBarChart() {

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
            labels: ['Janeiro', 'Fevereiro', 'Marco', 'Abril', 'Maio', 'Junho'],
            datasets: [{
                label: "Grafico barra do primeiro semestre do ano",
                backgroundColor: 'rgb (255, 99, 126)',
                borderColor: 'rgb (255, 99, 132)',
                data: [15, 39, 24, 55, 12, 99]
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

function createPieChart() {

    const dataPie = {
        labels: ['Area1', 'Area2', 'Area3', 'Area4'],
        datasets: [
            {
                label: 'Dataset 1',
                data: [12, 11, 22, 34],
                backgroundColor: [
                    '#36A2EB',
                    'rgb (154, 162, 135)',
                    'rgb (212, 99, 86)',
                    'rgb (154, 232, 21)'
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
