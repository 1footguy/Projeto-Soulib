function confirmarDelete(event) {
    document.getElementById("delete").addEventListener("submit", confirmarDelete);
let confirmar = confirm("Você tem certeza que deseja excluir este cliente?");
if (confirmar) {
    alert("Cliente deletado");
} else{
    event.preventDefault;
}
}