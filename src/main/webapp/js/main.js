document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.btn-delete').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            const memberId = this.getAttribute('data-id');
            const deleteLink = `${window.location.pathname.replace('/list', '')}/delete?id=${memberId}`;
            document.getElementById('confirmDeleteBtn').setAttribute('href', deleteLink);
            const modal = new bootstrap.Modal(document.getElementById('deleteModal'));
            modal.show();
        });
    });
});