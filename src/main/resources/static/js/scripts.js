
(function($) {
    const formElem = $('#form');
    formElem.on('submit', function (e) {
        e.preventDefault();
        $.ajax({
            url: formElem.attr('action'),
            type: formElem.attr('method'),
            data: formElem.serialize(),
            success: function (resultFragment) {
                $("#results").html(resultFragment);
                convertAndDisplayTotal();
                createCharts(getDonutDataFromView(), getBarDataFromView());

            },
            error: function (errorFragment) {
                $(".error-container").html(errorFragment);
            }
        })
    })

}(jQuery));

function convertAndDisplayTotal() {
    function getAndDisplay(id) {
        const value = parseFloat(document.getElementById(id).innerHTML);
        document.getElementById(id + '-text-amount').innerText = value.toLocaleString('de-DE', {
            style: "currency",
            currency: "EUR"
        });
    }

    ['final-investment-value', 'start-balance', 'total-contributions', 'total-interest'].forEach(id => {
        getAndDisplay(id);
    });
}

function getDonutDataFromView() {
    const startBalance = document.getElementById('start-balance').innerHTML;
    const totalContributions = document.getElementById('total-contributions').innerHTML;
    const totalInterest = document.getElementById('total-interest').innerHTML;
    return [startBalance, totalContributions, totalInterest];
}

function getBarDataFromView() {
    let annualYears = [];
    let annualContributions = [];
    let annualInterests = [];
    let annualEndBalances = [];
    document.querySelectorAll('.annual-year-text').forEach(elem => annualYears.push(elem.innerHTML));
    document.querySelectorAll('.annual-contribution-text').forEach(elem => annualContributions.push(elem.innerHTML));
    document.querySelectorAll('.annual-interest-text').forEach(elem => annualInterests.push(elem.innerHTML));
    document.querySelectorAll('.annual-end-balance-text').forEach(elem => annualEndBalances.push(elem.innerHTML));
    const annualStartBalance = Array(annualYears.length).fill(document.getElementById('start-balance').innerHTML);
    return { startBalances: annualStartBalance, years: annualYears, contributions: annualContributions, interests: annualInterests, endBalances: annualEndBalances };
}


function createCharts(donutData, barData) {
    createDonutChart(donutData);
    creatBarChart(barData);
}

function createDonutChart(data) {
    const labels = ['Starting Balance', 'Own Contributions', 'Interest Earned'];
    const ctx = document.getElementById('donut-chart');
    const donutChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                label: 'Donut chart',
                data: data,
                backgroundColor: [
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 190, 26, 1)',
                    'rgba(51, 204, 51, 1)'
                ],
                hoverOffset: 6
            }],
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom'
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return labels[tooltipItem.dataIndex] + ': ' + tooltipItem.formattedValue + ' €'
                        }
                    }
                },
                title: {
                    display: true,
                    text: 'Composition of Final Investment Value',
                    font: {
                        size: 18,
                        family: "'Roboto', 'sans-serif'"
                    }
                }
            }
        }
    });
}

function creatBarChart(data) {
    const ctx = document.getElementById('bar-chart');
    const barChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: data.years,
            datasets: [
            /*
            {
                label: 'Starting Balance',
                data: data.startBalances,
                borderWidth: 1,
                backgroundColor: 'rgba(54, 162, 235, 0.8)'
            },
             */
            {
                label: 'Own Contributions',
                data: data.contributions,
                borderWidth: 1,
                backgroundColor: 'rgba(255, 190, 26, 0.8)'
            },
            {
                label: 'Interest Earned',
                data: data.interests,
                borderWidth: 1,
                backgroundColor: 'rgba(51, 204, 51, 0.8)'
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    stacked: true
                },
                x: {
                    stacked: true
                }
            },
            plugins: {
                legend: {
                    position: 'bottom'
                },
                title: {
                    display: true,
                    text: 'Investment Growth per Year',
                    font: {
                        size: 18,
                        family: "'Roboto', 'sans-serif'"
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function (tooltipItem) {
                            return tooltipItem.dataset.label + ': ' + tooltipItem.formattedValue + ' €'
                        },
                        footer: function (tooltipItems) {
                            const totalValue = data.endBalances[tooltipItems[0].dataIndex];
                            return 'Total Investment Value: ' + totalValue + ' €';
                        },
                    }
                }
            },
            interaction: {
                intersect: false,
                mode: 'index'
            }
        }
    });
}

function addPhaseToForm() {
    const additionalPhasesBox = document.getElementById('additional-phases');
    additionalPhasesBox.insertAdjacentHTML('beforeend', newPhaseAsHtml(currentPhasesCount() + 1));
    if (currentPhasesCount() - 1 === 0) {
        document.getElementById('rm-btn').disabled = false;
    }
}

function removeLatestPhaseFromForm() {
    const additionalPhasesBox = document.getElementById('additional-phases');
    const latestChild = document.getElementById('phase-' + currentPhasesCount());
    additionalPhasesBox.removeChild(latestChild);
    if (currentPhasesCount() === 0) {
        document.getElementById('rm-btn').disabled = true;
    }
}

function currentPhasesCount() {
    const additionalPhasesBox = document.getElementById('additional-phases');
    return additionalPhasesBox.childElementCount;
}

const newPhaseAsHtml = (newPhaseCount) => {
    return '<div class="phase additional-phase" id="phase-' + newPhaseCount + '">\n' +
        '            <div class="phase-title">Phase ' + (newPhaseCount + 1)  + '</div>\n' +
        '               <div class="form-input-wrapper part-of-phase">\n' +
        '                   <label for="perContr-' + newPhaseCount + '">Periodic Contribution</label>\n' +
        '                   <span class="input-element currency">\n' +
        '                       <input id="perContr-' + newPhaseCount + '" name="periodicContribution" placeholder="100.0" required\n' +
        '                       min="0" type="number">\n' +
        '                   </span>\n' +
        '               </div>\n' +
        '               <div class="form-input-wrapper part-of-phase">\n' +
        '                   <label for="contrFreq-' + newPhaseCount + '">Contribution Frequency</label>\n' +
        '                   <select id="contrFreq-' + newPhaseCount + '" name="contributionFrequency">\n' +
        '                       <option value="1" selected>Monthly</option>\n' +
        '                       <option value="3">Quarterly</option>\n' +
        '                       <option value="6">Semi-Annually</option>\n' +
        '                       <option value="12">Annually</option>\n' +
        '                </select>\n' +
        '            </div>\n' +
        '            <div class="form-input-wrapper part-of-phase">\n' +
        '                <label for="annGrowth-' + newPhaseCount + '">Annual Growth</label>\n' +
        '                <span class="input-element percentage">\n' +
        '                <input id="annGrowth-' + newPhaseCount + '" name="annualGrowth" placeholder="8.0" required max="100"\n' +
        '                       min="-100" type="number">\n' +
        '                </span>\n' +
        '            </div>\n' +
        '            <div class="form-input-wrapper part-of-phase">\n' +
        '                <label for="durat-' + newPhaseCount + '">Duration</label>\n' +
        '                <span class="input-element years">\n' +
        '                <input id="durat-' + newPhaseCount + '" name="duration" placeholder="10" required max="100"\n' +
        '                       min="1" type="number">\n' +
        '                </span>\n' +
        '            </div>\n' +
        '        </div>'
}