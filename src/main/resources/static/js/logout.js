$(document).ready(function () {
    $('#logoutLink').on('click', function (e) {
        e.preventDefault();
        document.logoutForm.submit();
    })
});

// function customizeDropdownMenu() {
//     $('dropdown > a').on('click', function () {
//         location.href = this.href;
//     });
// }