 theLocalhostUrl = 'http://localhost:8080';
 theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com';
 url = theHostedSiteUrl;

$(document).ready(function () {
    console.log("custom-league.js loaded.")
});

$("#createCustomLeagueBtn").click(function () {
    var customLeagueModal = $('#customLeagueModal');
    $('#submitLeagueBtn').toggleClass('disable', true);
    customLeagueModal.modal('show');
});

$("#submitLeagueBtn").click(function () {
	grecaptcha.reset(); // on lock of sed button
	console.log("attempting to get custom ladder data.");
	// get user input
    var theLeagueId = $('#leagueId').val();
    var theLeagueName = $('#leagueName').val();
    console.log(" leagueId : " +theLeagueId+ "leagueName : " +theLeagueName);
    // close modal
    var customLeagueModal = $('#customLeagueModal');
    customLeagueModal.modal('hide');
    // reset user input text fields
    $('#leagueId').val("");
    $('#leagueName').val("");
    //make ajax call
    var textAreaContent = $('#form79textarea').val();

    $.ajax({
        url: url + '/custom-league',
        type: 'GET',
        dataType: "json",
        data: {
            leagueId: theLeagueId,
            leagueName: theLeagueName
        },
        success: function (results) {
            console.log("message sent successfully  : " + results.length + "ladder entries returned");
        },
        error: function (xhr, status, error) {
        	console.log("custom league not found.")
        }
    });
    
    
});

grecaptcha.ready(function () {
    grecaptcha.execute('6LePPoQUAAAAALMHr7-ZxEcgCBq4-atgP4hAXYB_', {
        action: 'homepage'
    }).then(function (token) {
        console.log("validated!");
    });
});

function enableBtn() {
    console.log("enableBtn()");
    $('#submitLeagueBtn').toggleClass('disable', false);

}
