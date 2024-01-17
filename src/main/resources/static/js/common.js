// $(document).ready(function () {
//     setTimeout(function () {
//         $('#successMessage').fadeOut();
//     }, 3000);
//
//
//     $('.delete-user').on("click", function () {
//         var userId = $(this).data('user-id');
//
//         $('#deleteUserModal').modal('show');
//
//         // Set giá trị cho input hidden
//         $('#modalUserId').val(userId);
//     });
//
//     $('#confirmDelete').on("click", function () {
//         var userId = $('#modalUserId').val();
//         console.log("user id", userId);
//
//         $.ajax({
//             type: 'POST',
//             url: '/shopme/admin/users/delete/' + userId,
//             headers: {
//                 'X-CSRF-TOKEN': $('#csrfToken').val()
//             },
//             success: function (response) {
//                 $('#deleteUserModal').modal('hide');
//                 window.location.reload();
//                 $('.message').text(response);
//             },
//             error: function (error) {
//                 console.error('Error deleting user:', error);
//             }
//         });
//     });
// });
//
// let clearFilter = () => {
//     window.location = "[[@{/shopme/admin/users}]]";
// }