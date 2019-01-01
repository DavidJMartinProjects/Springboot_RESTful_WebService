 theLocalhostUrl = 'http://localhost:8080';
 theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com';
 url = theHostedSiteUrl;

$(document).ready(function () {
    console.log("custom-league.js loaded.")
});

$("#createCustomLeagueBtn").click(function () {
    var customLeagueModal = $('#customLeagueModal');
//    $('#submitLeagueBtn').toggleClass('disable', true);
    customLeagueModal.modal('show');
});

$("#submitLeagueBtn").click(function () {
//	grecaptcha.reset(); // on lock of sed button
	console.log("attempting to get custom ladder data.");
	
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
    
	// get user input
    theLeagueId = $('#leagueId').val();
    theLeagueName = $('#leagueName').val();
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
            populateCustomLeagueTable(results);
          $("#carouselContainer").css('display', 'none');
        },
        error: function (xhr, status, error) {
        	console.log("custom league not found.")
        }
    });

    
    
});


var populateCustomLeagueTable = function (results) {
	console.log("inside populateCustomLeagueTable()");
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
    results
    .forEach(function (data) {
    	
        var twitchLink;
        var twitchUrl = "https://www.twitch.tv/";
        twitchUrl += data.twitch;
        if (data.twitch != "") {
            twitchLink = "<a href='" +
                twitchUrl +
                "' target='_blank'><img src='/images/twitch-logo.png' class='twitch-logo' title='" +
                data.twitch +
                "' style='width:18px;height:18px;border:0;'></a>";
        } else {
            twitchLink = "";
        }

	    toonName = data.character;
    	console.log("toonName : " + toonName);
	    var accountLink = getPoeAccount(data.account)
	    var ascendancyIcon = getAscendancyIcon(data.theClass);
	    var classColor = getColor(data.theClass);

	        $('#leagueInfoTable tbody').append(
	                '<tr>' +
		                '<td>' + data.rank + '</td>' +
		                '<td>' + data.account + '</td>' +
		                '<td>' + toonName + '</td>' +
		                '<td>' + data.level + '</td>' +
		                '<td><font color="' + classColor + '">' + ascendancyIcon + '</font></td>' +
		                '<td>' + "0" + '</td>' +
		                '<td>' + twitchLink + '</td>' +
	                '</tr>'
	            );
	        
    });   
	    var table = $("#leagueInfoTable").dataTable({
	        "iDisplayLength": 100,
	        responsive: true,
	        "pagingType": "full_numbers",
	        "order": [
	            [0, "asc"]
	        ],
	        stateSave: true,
	        "columnDefs": [{
	            "targets": [0], // column or columns numbers
	            type: 'formatted-num',
	        }],
	        deferRender: true,
	        "deferLoading": 400,
	        "columnDefs": [{
	            type: 'formatted-num',
	            targets: [6]
	        }],

});
    
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
	    
    new $.fn.dataTable.FixedHeader(table);

}

//grecaptcha.ready(function () {
//    grecaptcha.execute('6LePPoQUAAAAALMHr7-ZxEcgCBq4-atgP4hAXYB_', {
//        action: 'homepage'
//    }).then(function (token) {
//        console.log("validated!");
//    });
//});
//
//function enableBtn() {
//    console.log("enableBtn()");
//    $('#submitLeagueBtn').toggleClass('disable', false);
//
//}
