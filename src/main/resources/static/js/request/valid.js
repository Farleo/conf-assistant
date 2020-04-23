$(document).ready(function () {
    $("#contactForm").submit(function (event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        sendCredential();
    });
});

function sendCredential() {
    var token = $('#_csrf').attr('content');
    var header = $('#_csrf_header').attr('content');
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
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data, textStatus, xhr) {
        },
        error: function (xhr, status, error) {
            if (xhr.status === 200) {
                alert("On your new email address was sent link. Please check your email")
                console.log(document.location.origin);
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






