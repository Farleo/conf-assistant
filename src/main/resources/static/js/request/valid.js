$(document).ready(function () {
    $("#contactForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        sendCredential();
    });
});

function sendCredential() {
    // PREPARE FORM DATA
    var formData = {
        userId: $("#userId").val(),
        email: $("#email").val()
    };

    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: document.location.origin + "/edit/speaker/valid/contacts",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (data, textStatus, xhr) {
        },
        error: function (xhr, status, error) {
            if (xhr.status === 200) {
/*                var code = prompt("Emter verifiying code")*/
                console.log(document.location.origin);
/*                sendCode(code);*/
            } else {
                var json = JSON.parse(xhr.responseText);
                $('#ContactError div').empty();
                var notValid =
                    "<div class=\"alert alert-danger\">" + json.email + "</div>";
                $('#ContactError div').append(notValid);
            }
        }
    });
}

function sendCode(code) {
    // PREPARE FORM DATA
    var formData = {
        code: code
    };

    // DO POST
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: document.location.origin + "/edit/speaker/valid/cod",
        data: JSON.stringify(formData),
        dataType: 'json',
        success: function (data, textStatus, xhr) {

        },
        error: function (xhr, status, error) {

        }
    });
}






