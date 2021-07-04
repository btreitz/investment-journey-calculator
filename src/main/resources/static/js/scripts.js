
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
    document.querySelectorAll('.hidden-annual-year-text').forEach(elem => annualYears.push(elem.innerHTML));
    document.querySelectorAll('.hidden-annual-contribution-text').forEach(elem => annualContributions.push(elem.innerHTML));
    document.querySelectorAll('.hidden-annual-interest-text').forEach(elem => annualInterests.push(elem.innerHTML));
    document.querySelectorAll('.hidden-annual-end-balance-text').forEach(elem => annualEndBalances.push(elem.innerHTML));
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
    const windowWidth = window.innerWidth;
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
                hoverOffset: 4
            }],
        },
        options: {
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 14,
                            family: "'Roboto', 'sans-serif'",
                        }
                    }
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return labels[tooltipItem.dataIndex] + ': ' + tooltipItem.formattedValue + ' €'
                        }
                    },
                    bodySpacing: 4
                },
                title: {
                    display: true,
                    text: 'Composition of Final Investment Value',
                    font: {
                        size: 18,
                        family: "'Roboto', 'sans-serif'",
                        weight: 'normal'
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
            responsive: true,
            maintainAspectRatio: true,
            aspectRatio: 2,
            datasets: {
                bar: {
                    maxBarThickness: 60,
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    stacked: true,
                    ticks: {
                        // Include a dollar sign in the ticks
                        callback: function(value, index, values) {
                            return parseFloat(value).toLocaleString('de-DE', {
                                style: "currency",
                                currency: "EUR"
                            });
                        }
                    }
                },
                x: {
                    stacked: true
                }
            },
            plugins: {
                legend: {
                    position: 'bottom',
                    labels: {
                        font: {
                            size: 14,
                            family: "'Roboto', 'sans-serif'",
                        }
                    }
                },
                title: {
                    display: true,
                    text: 'Investment Growth per Year',
                    font: {
                        size: 18,
                        family: "'Roboto', 'sans-serif'",
                        weight: 'normal'
                    }
                },
                tooltip: {
                    callbacks: {
                        beforeTitle: function (tooltipItem) {
                            return 'In ' + tooltipItem[0].label + ',';
                        },
                        title: function (tooltipItem) {
                            let sum = 0;
                            for (let item of tooltipItem) {
                                sum += item.parsed.y;
                            }
                            const sumFormatted = sum.toLocaleString('de-DE', {
                                style: "currency",
                                currency: "EUR"
                            });
                            return 'the growth is ' + sumFormatted;
                        },
                        label: function (tooltipItem) {
                            return tooltipItem.dataset.label + ': ' + tooltipItem.formattedValue + ' €'
                        },
                        footer: function (tooltipItems) {
                            const totalValue = parseFloat(data.endBalances[tooltipItems[0].dataIndex]);
                            const formattedValue = totalValue.toLocaleString('de-DE', {
                                style: "currency",
                                currency: "EUR"
                            });
                            return 'Total Investment Value: ' + formattedValue;
                        },
                    },
                    titleFont: {
                        size: 14,
                        weight: 'normal'
                    },
                    labelFont: {
                        size: 14,
                        weight: 'normal'
                    },
                    footerFont: {
                        size: 14,
                        weight: 'normal'
                    },
                    bodySpacing: 4
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
    const additionalPhasesBox = document.getElementById('all-phases');
    const newPhasesCount = currentPhasesCount() + 1;
    additionalPhasesBox.insertAdjacentHTML('beforeend', newPhaseAsHtml(newPhasesCount));
    if (newPhasesCount - 2 === 0) {
        document.getElementById('rm-btn').disabled = false;
    }
}

function removeLatestPhaseFromForm() {
    const allPhasesElem = document.getElementById('all-phases');
    const phasesCount = currentPhasesCount();
    const latestChild = document.getElementById('phase-' + phasesCount);
    allPhasesElem.removeChild(latestChild);
    if (phasesCount - 2 === 0) {
        document.getElementById('rm-btn').disabled = true;
    }
}

function currentPhasesCount() {
    const allPhasesElem = document.getElementById('all-phases');
    return allPhasesElem.childElementCount;
}

const newPhaseAsHtml = (newPhaseCount) => {
    return '<div class="phase additional-phase" id="phase-' + newPhaseCount + '">\n' +
        '            <div class="phase-title">Phase ' + newPhaseCount  + '</div>\n' +
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