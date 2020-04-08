$(document).ready(function () {

    $("#QuestionForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        askQuestion();
    });

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

    var isPresent = $("#isPresentUser").val();
    if (isPresent === 'true') {
        show();
    }

})
;

var orderBy = "Rating";
var intervalID;

function fun1() {
    var chbox;
    chbox=document.getElementById('AutoRefresh');
    console.log(chbox);

    if (chbox.checked) {
        intervalID = setInterval(show, 1500);
    }
    else {
        clearInterval(intervalID);
    }
}

function askQuestion() {
    // PREPARE FORM DATA
    var formData = {
        question: $("#question").val(),
        topic: $("#topicId").val(),
        user: $("#currentGuest").val()
    };

    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/save-question",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function () {
            show()
        }

    });
    resetData();

    function resetData() {
        $("#question").val("");
    }
}

function show() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/get-all-questions/" + $("#topicId").val() + "/" + orderBy,
        cache: false,
        success: function (result) {
            $('#getResultDiv div').empty();
            $.each(result, function (i, question) {
                if (!question.deleted && !question.selected) {
                    var variable =
                        "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                        "<div class='row'>" +
                        "<button id='" + i + "' type='button' class='btn btn-link'>" +
                        "<i class='fa fa-heart' style='color: #9c9c9c;'>" + ' ' + question.rating + "</i>" +
                        "</button>" +
                        "<div class='card-title rounded-pill' style='background-color: #ebebeb; padding-left: 1.1em; padding-right: 1.1em; padding-bottom: 0.3em; padding-top: 0.3em'>" + question.question +
                        "<div class='text-muted card-subtitle' style='font-size: 0.7em; margin-top: 1px'>" + question.created +
                        "</div>" +
                        "</div>" +

                        "</div>" +
                        "</div>";

                    $.each(question.likesSet, function (j, userId) {
                        if (userId == $("#currentGuest").val()) {
                            variable =
                                "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                                "<div class='row'>" +
                                "<button id='" + i + "' type='button' class='btn btn-link'>" +
                                "<i class='fa fa-heart' style='color: #d80700;'>" + ' ' + question.rating + "</i>" +
                                "</button>" +
                                "<div class='card-title rounded-pill' style='background-color: #ebebeb; padding-left: 1.1em; padding-right: 1.1em; padding-bottom: 0.3em; padding-top: 0.3em'>" + question.question +
                                "<div class='text-muted card-subtitle' style='font-size: 0.7em; margin-top: 1px'>" + question.created +
                                "</div>" +
                                "</div>" +

                                "</div>" +
                                "</div>";
                        }
                    });

                    $('#getResultDiv .list-group').append(variable);
                    $('#' + i).click(function () {
                        sendLike(i, question);
                    });
                }
                if (question.selected) {
                    console.log(question);
                    $('#selectedQuestion div').empty();
                    var selectedQ =
                        "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                        "<div class='row'>" +
                        "<div class='btn'>" +
                        "<i class='fa fa-heart' style='color: #9c9c9c;'>" + ' ' + question.rating + "</i>" +
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

function sendLike(i, question) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "http://localhost:8080/like/" + question.questionId,
        dataType: 'json',
        success: function (isLike) {
            show()
        }
    });
}
