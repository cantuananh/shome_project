// $(document).ready(function () {
//     $('#fileImage').change( function (){
//         let fileSize = this.files[0].size;
//         if (fileSize > 1048576) {
//             this.setCustomValidity("You must choose an image less than 1Mb.")
//             this.reportValidity();
//         } else {
//             this.setCustomValidity("")
//             showImageThumbnail(this);
//         }
//     })
// });
//
// function showImageThumbnail(fileInput) {
//     var file = fileInput.files[0];
//     var reader = new FileReader();
//
//     reader.onload = function (e){
//         $('#thumbnail').attr("src", e.target.result);
//     }
//
//     reader.readAsDataURL(file);
// }
//
// function checkEmailUnique(form) {
//     let userId = $('#userId').val();
//
//     if (userId) {
//         form.submit();
//         return false;
//     }
//
//     let url = "[[@{/shopme/admin/users/check-email}]]";
//     let csrfValue = $("input[name='_csrf']").val();
//     let userEmail = $('#email').val();
//     let params = {
//         userId: userId,
//         email: userEmail,
//         _csrf: csrfValue
//     };
//
//     $.post(url, params, function (response) {
//         if (response == "Ok") {
//             form.submit();
//         } else if (response == "Duplicated") {
//             // showModalDialog("Duplicated email", "There is another user having the email " + userEmail);
//             alert("There is another user having the email " + userEmail);
//         }
//     });
//
//     return false;
// }
//
//
// function showModalDialog(title, message) {
//     $("#modalTitle").text(title);
//     $("#modalBody").text(message);
//     $("#modalDialog").modal();
// }