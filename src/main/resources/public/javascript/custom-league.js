 theLocalhostUrl = 'http://localhost:8080';
 theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com/ladders';
 url = theLocalhostUrl;

$(document).ready(function () {
    console.log("custom-league.js loaded.")
});

$("#createCustomLeagueBtn").click(function () {
    var customLeagueModal = $('#customLeagueModal');
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
            console.log("message sent successfully  : " + results);
        },
        error: function (xhr, status, error) {
        	console.log("custom league not found.")
        }
    });
    
    
});

$("#btn-send").click(function () {
    
    
    var textAreaContent = $('#form79textarea').val();

    $.ajax({
        url: 'https://immense-headland-50105.herokuapp.com/' + 'mail/send',
        type: 'POST',
        dataType: "json",
        data: {
            userName: "Feedback Message : \n\n",
            message: textAreaContent
        },
        success: function (msg) {
            console.log("message sent successfully  : " + msg);
        },
        error: function (xhr, status, error) {}
    });

    var mymodal1 = $('#modalPoll-1');
    $('#form79textarea').val("");
    mymodal1.modal('hide');
});
