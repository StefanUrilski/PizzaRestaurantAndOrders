let roles = [[${userViewModel.roles}]];

if (roles.indexOf('ROLE_ROOT') > -1) {
    $('#root-radio').prop('checked', true);
    $('#admin-radio').prop('disabled', true);
    $('#moderator-radio').prop('disabled', true);
    $('#courier-radio').prop('disabled', true);
    $('#user-radio').prop('disabled', true);
} else if (roles.indexOf('ROLE_ADMIN') > -1) {
    $('#admin-radio').prop('checked', true);
} else if (roles.indexOf('ROLE_MODERATOR') > -1) {
    $('#moderator-radio').prop('checked', true);
} else if (roles.indexOf('ROLE_COURIER') > -1) {
    $('#courier-radio').prop('checked', true);
} else if (roles.indexOf('ROLE_USER') > -1) {
    $('#user-radio').prop('checked', true);
}

$(function () {
    $('.form-check-inline>input, .form-check-inline>label').click(function () {
        let userEmail = $('#user-email').text();
        let role = $(this).val();

        let data = {
            email: userEmail,
            role: role
        };

        $.ajax({
            type : 'POST',
            contentType: 'application/json',
            url: 'http://localhost:8000/profile/roleEdit',
            data: data
        });
    });
});
