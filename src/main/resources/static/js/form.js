
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
                createCharts(getDataFromView());

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

function getDataFromView() {
    const startBalance = document.getElementById('start-balance').innerHTML;
    const totalContributions = document.getElementById('total-contributions').innerHTML;
    const totalInterest = document.getElementById('total-interest').innerHTML;
    return [startBalance, totalContributions, totalInterest];
}

function createCharts(data) {
    const ctx = document.getElementById('donut-chart');
    const donutChart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Starting Balance', 'Own Contributions', 'Interest Earned'],
            datasets: [{
                label: 'Test donut chart',
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
                title: {
                    display: true,
                    text: 'Composition of final investment value',
                    font: {
                        size: 18,
                        family: "'Roboto', 'sans-serif'"
                    }
                }
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