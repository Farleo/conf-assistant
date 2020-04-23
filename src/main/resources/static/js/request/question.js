$(document).ready(function () {



    $("#QuestionForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        askQuestion();
    });


    $("#refresh").click(function () {
        show();
        isQuestionAllowed($("#topicId").val());
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

    var isRegisteredOnConf = $("#isRegisteredOnConf").val();
    if (isRegisteredOnConf === 'true') {
        show();
    }

})
;
var isAllowedQuestion;
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
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
    // PREPARE FORM DATA
    var formData = {
        question: $("#question").val(),
        topic: $("#topicId").val(),
        user: $("#currentGuest").val()
    };

    // DO POST
    $.ajax({
        type: "POST",
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        contentType: "application/json",
        url: document.location.origin + "/save-question",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function () {
            show()
        },
        error: function(xhr,status,error) {
            console.log("error");
            if (xhr.status === 403) {
                $('#QuestionForm').empty();
                var variable = "<div id=\"QuestionBlock3\" class=\"card border-0\">\n" +
                    "                            <p>The ability to ask questions is currently disabled</p>\n" +
                    "                        </div>";
                $('#QuestionForm').append(variable);
            }
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
        url: document.location.origin + "/get-all-questions/" + $("#topicId").val() + "/" + orderBy,
        cache: false,
        success: function (result) {
            $('#getResultDiv div').empty();
            $.each(result, function (i, question) {
                if (!question.deleted && !question.selected) {
                    var variable =
                        "<div class='list-group-item border-0' style='padding-bottom: 0em'>" +
                        "<div class='row'>" +
                        "<button id='" + i + "' type='button' class='btn btn-link heart' > " +
                        "<i class='fa fa-heart' >" + ' ' + question.rating + "</i>" +
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
        url: document.location.origin + "/like/" + question.questionId,
        dataType: 'json',
        success: function (isLike) {
            show()
        }
    });
}

function isQuestionAllowed(topicId) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: document.location.origin + "/question/status/" + topicId,
        dataType: 'json',
        success: function (isAllowed) {
            if (isAllowed) {
                $('#QuestionForm').empty();
                variable = "<input style=\"margin-right: 10px; margin-left: 10px\" id=\"question\" class=\"form-control col\"\n" +
                    "                                   placeholder=\"Write your question\">\n" +
                    "                            <button style=\"background-color: rgba(255,103,55,0.94); max-width: max-content\"\n" +
                    "                                    class=\"btn col text-white\" type=\"submit\"> Send\n" +
                    "                            </button>";
                $('#QuestionForm').append(variable);
            } else {
                $('#QuestionForm').empty();
                variable = "<div id=\"QuestionBlock3\" class=\"card border-0\">\n" +
                    "                            <p>The ability to ask questions is currently disabled</p>\n" +
                    "                        </div>";
                $('#QuestionForm').append(variable);
            }
        }
    });
}
