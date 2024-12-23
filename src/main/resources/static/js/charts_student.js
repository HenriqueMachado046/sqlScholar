
// const xValues = [5, 8, 1, 2, 4, 7, 5, 13, 11, 12, 17]; -> funcional
// const yValues = [7, 8, 8, 9, 9, 9, 10, 11, 14, 14, 15]; -> funcional
//Aqui está um momento de escolha. Os gráficos serão definidos previamente ou poderão ser criados?
//Gráfico de questões acertadas por este aluno;
//3 indicadores estatísticos para aluno e professores;
//2 processos de gamificação;
//1 gráfico pizza, 1 Gráfico Linha, 1 Gráfico barra; OK

// Gráfico linha = Acertos vs Erros (Média);
// Gráfico pizza = Tentadas vs questões cadastradas;
// Gráfico barra = Acertos vs Erros vs Total de cadastradas (Total);

//Tabela

function createChart(xValues, yValues) {
    const newXvalues = []
    const newYvalues = []

    for (let index = 1; index <= xValues; index++) {
        newXvalues.push(index);
    }

    for (let index = 1; index <= yValues; index++) {
        newYvalues.push(index);
    }

    
    if (document.getElementById("myChart") === null) {
        const newParagraph = document.createTextNode("Grafico de acertos e erros");
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

    //Aqui o problema é que não tem um vetor de dados.
    //Resolvido
    new Chart(document.getElementById('myChart').id, {
        type: "line",
        data: {
            labels: newYvalues,
            datasets: [{
                fill: false,
                lineTension: 0,
                backgroundColor: "rgba(0,0,255,1.0)",
                borderColor: "rgba(0,0,255,0.1)",
                data: newXvalues
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
}
//funcionando 100%
function createBarChart(xValues, yValues, zValues) {

    console.log(yValues)

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
            labels: ['Acertos', 'Erros', 'Tentativas'],
            datasets: [{
                label: "Acertos vs Erros vs Tentativas",
                data: [parseInt(xValues), parseInt(yValues), parseInt(zValues)]
            }]
        },
        options:{
            responsive: true,
            legend:{
                position: 'top',
            },
            title:{
                display:true,
                text: 'Acertos vs Erros vs Tentativas'
            },
            scales:{
                x:{
                    ticks:{
                        display: true,
                        autoSkip: false
                    }
                },
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    })         
};

function createPieChart(xValues, zValues) {

    const dataPie = {
        labels: ['Tentadas', 'Cadastradas'],
        datasets: [
            {
                label: 'Tentadas vs questões cadastradas',
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
                text: 'Tentadas vs questoes cadastradas'
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
