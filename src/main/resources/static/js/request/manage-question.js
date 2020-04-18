$(document).ready(function () {

    $("#refresh").click(function () {
        show();
    });


    $("#OrderBy").click(function () {
        $('#Text').empty();
        var button;
        if (orderBy === "Created") {
            button = "Order by rating";
            orderBy = "Rating";
        } else {
            button = "Order by time";
            orderBy = "Created";
        }
        $('#Text').append(button);
        show();
    });

    show();

    var variable;
    $("#enableButton").empty();
    if ($("#isEnable").val() === 'true') {
        variable = "Disable Questions";
    } else {
        variable = "Enable Questions";
    }
    $("#enableButton").append(variable);


    $("#enableButton").click(function () {
        console.log($("#enableButton"));
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: document.location.origin + "/enable/" + $('#topicId').val(),
            success: function (result) {
                $("#enableButton").empty();
                var variable;
                if (result) {
                    alert("All question was Enabled");
                    variable = "Disable Questions";
                } else {
                    alert("All question was Disabled");
                    variable = "Enable Questions";
                }
                $("#enableButton").append(variable);
            }
        });
    });
});


var orderBy = "Rating";
var intervalID;

function fun1() {
    var chbox;
    chbox = document.getElementById('AutoRefresh');

    if (chbox.checked) {
        intervalID = setInterval(show, 1500);
    } else {
        clearInterval(intervalID);
    }
}

function show() {
    $.ajax({
        type: "GET",
        url: document.location.origin + "/get-all-questions/" + $("#topicId").val() + "/" + orderBy,
        cache: false,
        success: function (result) {
            $('#getResultDiv div').empty();
            $.each(result, function (i, question) {
                if (!question.deleted && !question.selected) {
                    var variable =
                        "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                        "<div class='row'>" +
                        "<div class='btn'>" +
                        "<i class='fa fa-heart' style='color: #d80700;'>" + ' ' + question.rating + "</i>" +
                        "</div>" +
                        "<div class='card-title rounded-pill' style='background-color: #ebebeb; padding-left: 1.1em; padding-right: 1.1em; padding-bottom: 0.3em; padding-top: 0.3em'>" + question.question +
                        "<div class='text-muted card-subtitle' style='font-size: 0.7em; margin-top: 1px'>" + question.created +
                        "</div>" +
                        "</div>" +
                        "<div id='select" + i + "' class='btn btn-outline-light'>Select</div>" +
                        "</div>" +
                        "</div>";

                    $('#getResultDiv .list-group').append(variable);
                    $('#select' + i).click(function () {
                        select(question);
                    });
                }
                if (question.selected) {
                    $('#selectedQuestion div').empty();
                    var selectedQ =
                        "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                        "<div class='row'>" +
                        "<div class='btn'>" +
                        "<i class='fa fa-heart' style='color: #d80700;'>" + ' ' + question.rating + "</i>" +
                        "</div>" +
                        "<div class='card-title rounded-pill' style='background-color: #ebebeb; padding-left: 1.1em; padding-right: 1.1em; padding-bottom: 0.3em; padding-top: 0.3em'>" + question.question +
                        "<div class='text-muted card-subtitle' style='font-size: 0.7em; margin-top: 1px'>" + question.created +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>";
                    $('#selectedQuestion .text-muted').append(selectedQ);
                }
            });
        },
        error: function (e) {
            $("#getResultDiv").html("<strong>Error</strong>");
            console.log("ERROR: ", e);
        }
    });
}

function select(question) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: document.location.origin + "/select/" + question.questionId + "/" + $('#topicId').val(),
        dataType: 'json',
        success: function (result) {
            if (result) {
                show();
            } else {
                show();
            }
        }
    });
}

