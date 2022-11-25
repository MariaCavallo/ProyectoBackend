function deleteBy(id){
    const url = '/pacientes/' + id;
    const settings = {
        method = 'DELETE'
    }
    fetch(url, settings)
        .then(response => response.json())

    let row_id = "#tr" + id;
    document.querySelector()
}