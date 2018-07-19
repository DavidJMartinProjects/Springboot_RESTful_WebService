theLocalhostUrl = 'http://localhost:8080/ladders';
theHostedSiteUrl = 'http://immense-headland-50105.herokuapp.com/ladders';

$(document).ready(function() {
	console.log("index.html loaded.")
});

$("#selectLeagueInputGroup").change(function(){
    var selected = $('#selectLeagueInputGroup option:selected').val();
    console.log("Selected League : " + selected);      
    getLeagueData(selected);
});

var getLeagueData = function(selectedLeague) {
    $.ajax({
        url: theLocalhostUrl,
        type: 'GET',
        dataType: "json",
        data : {
        	league : selectedLeague
        },
        success: function(results) {
        	console.log(results)
            populateLeagueTable(results);
        },
        error: function(error) {
            console.log("getLeagueData error : " + error.responseText, "error");
        }
    });
};

var populateLeagueTable = function(results) {
    $('#leagueInfoTable').dataTable().fnDestroy();
    $("#leagueInfoTable tbody").empty();
    $("#leagueInfoTableContainer").css({
        "display": "block"
    });
    
    results.forEach(function(data) {
	    $('#leagueInfoTable tbody').append(
	            '<tr>' +
		    		'<td>' + data.rank + '</td>' +
		    		'<td>' + data.character + '</td>' +
		    		'<td>' + data.account + '</td>' +
		    		'<td>' + data.level + '</td>' +
		    		'<td>' + data.theClass + '</td>' +
		    		'<td>' + data.challenges + '</td>' +
		    		'<td>' + data.experience + '</td>' +
	    		'</tr>'
	     );
    });
    
    $('#leagueInfoTable').DataTable({        
//        searching: true,
//        deferRender: true,
//        scrollY: 310,
//        scrollCollapse: true,
//        scroller: true
    	"iDisplayLength": 50
    });
};