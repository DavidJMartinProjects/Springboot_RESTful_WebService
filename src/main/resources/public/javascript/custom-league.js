 theLocalhostUrl = 'http://localhost:8080';
 theHostedSiteUrl = 'https://immense-headland-50105.herokuapp.com';
 url = theHostedSiteUrl;
 theLeagueId = "";
 theLeagueName = "";

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
	
    theLeagueId = $('#leagueId').val();
    theLeagueName = $('#leagueName').val();
    
    var mymodal = $('#frameModalBottom');
    mymodal.find('.modal-body').text('building ladder for custom league id : ' + theLeagueId + '.');
    mymodal.modal('show');
	
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
    
	// get user input

    console.log(" leagueId : " +theLeagueId+ "leagueName : " +theLeagueName);
    // close modal
    var customLeagueModal = $('#customLeagueModal');
    customLeagueModal.modal('hide');
    // reset user input text fields
    $('#leagueId').val("");
    $('#leagueName').val("");
    //make ajax call
    var textAreaContent = $('#form79textarea').val();
    getLeagueDataTable(theLeagueId, theLeagueName);
 
});

var getLeagueDataTable = function (theLeagueId, theLeagueName) {
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
	            var mymodal = $('#frameModalBottom');
	            mymodal.find('.modal-body').text('building ladder for custom league id : ' + theLeagueId + '.');
	            mymodal.modal('hide');
	          $("#carouselContainer").css('display', 'none');
	        },
	        error: function (xhr, status, error) {
	        	console.log("custom league not found.")
	            var mymodal = $('#frameModalBottom');
	        	var text = "custom league not found.";
	            mymodal.find('.modal-body').text(text);
	            mymodal.modal('show');
	        }
	    });
}

var populateCustomLeagueTable = function (results) {
	console.log("inside populateCustomLeagueTable()");
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
 
    var timestamp;
    results
    .forEach(function (data) {
    	timestamp = data.timeStamp;
    	console.log("timestamp : " + timestamp);
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
        
        exp = "";
        if (data.xph == 0 || data.xph == null || data.xph == "null") {
            exp = "-";
        } else {
            exp = data.xph;
        }

        var xphColor = getXphColor(exp);
        
        var account = "";
        if (data.online == "true") {
            account = "<img class='img-valign' src='/images/green-icon.png' title='online' />   " +
                data.account;
        } else {
            account = "<img class='img-valign' src='/images/red-icon.png' title='offline' />   " +
                data.account;
        }
        
        var theRankDifference = "";
        var rankDifference = data.rankDifference;

        if (rankDifference < 0) {
            theRankDifference = "<span class='arrow-down'></span>&ensp;(" +
                data.rankDifference + ")";
        } else if (rankDifference >= 0 && rankDifference < 1) {
            theRankDifference = "";
        } else if (rankDifference >= 1) {
            theRankDifference = "<span class='arrow-up'></span>&ensp;(+" +
                data.rankDifference + ")";
        }

	    var character = data.character;
	    var accountLink = getPoeAccount(data.account)
	    var ascendancyIcon = getAscendancyIcon(data.theClass);
	    var classColor = getColor(data.theClass);
        var challenge_icon = getChallengeIcon(data.challenges);
        
        if (data.dead == "true") {
            character += " <i id='deadStatus'>(dead)</i>";
        }

        var rowDiv 
        if(data.dead == "true") {
        	rowDiv = '<tr class = "deadChar">';
        } else {
        	rowDiv = '<tr>';
        }
        $('#leagueInfoTable tbody').append(        		
    		rowDiv +
            	'<td>' + theRankDifference + '</td>' +
                '<td>' + data.rank + '</td>' +
                '<td><a href=' + accountLink +' target="_blank">' + account +'</a></td>' +
                '<td>' + challenge_icon + "  " + character +'</td>' +
                '<td>' + data.level + '</td>' +
                '<td><font color="' + classColor + '">' + ascendancyIcon + "  " + data.theClass +'</font></td>' +
                '<td class="' +xphColor +'">' + exp +'</td>' +
			    '<td>' + '<div class="progress">' +
						   '<div class="progress-bar bg-dark" style="width:' + data.levelProgressBar + '%">' + 
						   '</div>' + 
						 '</div>'+ 
				'</td>' +
                '<td>' + data.experience+ '</td>' +
                '<td>' + twitchLink + '</td>' +
            '</tr>'
         );
	        
    });   

    var table = $("#leagueInfoTable").DataTable({
		"iDisplayLength" : 100,
		responsive : true,
		"pagingType" : "full_numbers",
		"order" : [ [ 1, "asc" ] ],
		stateSave : true,
		"columnDefs" : [ {
			"targets" : [ 0 ], // column or columns numbers
			type : 'formatted-num',
		} ],
		deferRender : true,
		"deferLoading" : 400,
		"columnDefs" : [ {
			type : 'formatted-num',
			targets : [ 6 ]
		} ],

	});
	    
    $("#leagueInfoTable_wrapper").prepend('<span id="lastUpdatedMsg"></span>');
    $('#lastUpdatedMsg').text("ranks last updated : " + timestamp + ".");
    
    hideUpdatedMessage();
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
    new $.fn.dataTable.FixedHeader(table);
    
    setTimeout(function () {
    	showUpdatedMessage();
    	
        getLeagueDataTable(theLeagueId, theLeagueName);
//        hideUpdatedMessage();
//        $('#lastUpdatedMsg').text("ranks last updated : " + timeStamp + ".");

    }, 5 * 60 * 1000);
//    	 }, 1 * 30 * 1000);

}

var showUpdatedMessage = function () {
    var mymodal = $('#ranksUpdatedModal');
    mymodal.find('.modal-body').text('updating ladder ranks.');
    mymodal.modal('show');
}

var hideUpdatedMessage = function () {
    var mymodal = $('#ranksUpdatedModal');
    mymodal.modal('hide');
}

var getChallengeIcon = function (numberOfChallenges) {
    return "<img class='icon' src='/challenge_images/" + numberOfChallenges +
        ".png' title='" + numberOfChallenges + 
        " challenges completed' />";
}

var getXphColor = function (xph) {
    var xpPerHour = xph;
    if (xpPerHour < "0" && xpPerHour != "-") {
        formatXph(xpPerHour);
        return "xp-per-hour-red";
    } else if (xpPerHour >= "0") {
        formatXph(xpPerHour);
        return "xp-per-hour-green";
    } else {
        return "";
    }
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
