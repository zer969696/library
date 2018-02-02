function deleteBook(id) {
    if (confirm('Удалить книгу?')) {
        makeRequest('get', '/api/books/delete/' + id, null).done(function (result) {
            reloadView();
        });
    } else {
        alert('Книга не была удалена!');
    }
}

function showModal(currentUser) {
    let modalContent = `
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-isbn">
            <label class="mdl-textfield__label" for="modal-isbn">Введите ISBN...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-title">
            <label class="mdl-textfield__label" for="modal-title">Введите название...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-author">
            <label class="mdl-textfield__label" for="modal-author">Введите автора...</label>
        </div>
    `;

    showDialog({
        id: 'dialog-id',
        title: 'Добавление книги',
        text: modalContent,
        negative: {
            id: 'cancel-button',
            title: 'Отменить',
            onClick: function () {
                alert("Книга не была добавлена")
            }
        },
        positive: {
            id: 'ok-button',
            title: 'Сохранить',
            onClick: function () {
                let postData = {
                    isbn: $('#modal-isbn').val(),
                    title: $('#modal-title').val(),
                    author: $('#modal-author').val()
                };

                makeRequest('post', '/api/books/add', postData).done(
                    function (result) {
                        if (result === 1) {
                            alert('Книга успешно добавлена');
                            reloadView(currentUser);
                        }
                    }
                )
            }
        },
        cancelable: true,
        contentStyle: {'max-width': '500px'},
        onLoaded: function () {
        },
        onHidden: function () {
        }
    });
}

function showEditModal(currentUser, isbn, title, author, id) {
    let modalContent = `
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-isbn" value="` + isbn + `">
            <label class="mdl-textfield__label" for="modal-isbn">Введите ISBN...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-title" value="` + title + `">
            <label class="mdl-textfield__label" for="modal-title">Введите название...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-author" value="` + author + `">
            <label class="mdl-textfield__label" for="modal-author">Введите автора...</label>
        </div>
    `;

    showDialog({
        id: 'dialog-id',
        title: 'Изменение книги',
        text: modalContent,
        negative: {
            id: 'cancel-button',
            title: 'Отменить',
            onClick: function () {
                alert("Книга не была изменена")
            }
        },
        positive: {
            id: 'ok-button',
            title: 'Сохранить',
            onClick: function () {
                let postData = {
                    isbn: $('#modal-isbn').val(),
                    title: $('#modal-title').val(),
                    author: $('#modal-author').val(),
                    id
                };

                makeRequest('post', '/api/books/edit', postData).done(
                    function (result) {
                        if (result === 1) {
                            alert('Книга успешно изменена');
                            reloadView(currentUser);
                        }
                    }
                )
            }
        },
        cancelable: true,
        contentStyle: {'max-width': '500px'},
        onLoaded: function () {
        },
        onHidden: function () {
        }
    });
}

function sort(orderBy, sortDir, currentUser) {
    if (orderBy === 'author') {
        let authorElement = $('#author');
        let titleElement = $('#title');

        authorElement.css('background-color', 'rgba(0,0,0,0.1)');
        titleElement.css('background-color', 'unset');

        let onClickEvent = authorElement.attr('onclick');
        authorElement.attr('onclick', changeSortDir(onClickEvent));
    }

    if (orderBy === 'title') {
        let authorElement = $('#author');
        let titleElement = $('#title');

        titleElement.css('background-color', 'rgba(0,0,0,0.1)');
        authorElement.css('background-color', 'unset');

        let onClickEvent = titleElement.attr('onclick');
        titleElement.attr('onclick', changeSortDir(onClickEvent));
    }

    reloadViewSort(orderBy, sortDir, currentUser);
}

function changeSortDir(attr) {
    let tmp = attr.split(',')[1];
    let tmpSplit = attr.split(',');
    if (tmp === " 'asc'") {
        return tmpSplit[0] + ", 'desc'," + tmpSplit[2];
    } else if (tmp === " 'desc'") {
        return tmpSplit[0] + ", 'asc'," + tmpSplit[2];
    }
}

function showMore(page, currentUser) {
    $('button.show-more-button').attr('onclick', 'showMore(' + ++page + ', \'' + currentUser + '\')');

    togglePreloader();

    makeRequest('get', '/api/books/all', {page: page}).done(
        function (result) {
            let tableBody = $('tbody');

            for (let i = 0; i < result.length; i++) {
                let element = createBookElement(result[i].isn, result[i].title, result[i].author, result[i].user.name, result[i].id, currentUser);
                tableBody.append(element);
            }

            setTimeout(function () {
                togglePreloader();
            }, 1000);
        }
    )
}

function putBook(id, currentUser) {
    makeRequest('get', '/api/books/put/' + id, null).done(
        function () {
            reloadView(currentUser);
        }
    )
}

function takeBook(id, currentUser) {
    makeRequest('get', '/api/books/take/' + id, null).done(
        function () {
            reloadView(currentUser);
        }
    )
}

function makeRequest(type, url, data) {
    return $.ajax({
        url: url,
        data: data,
        type: type
    });
}

function reloadView(currentUser) {
    let tableBody = $('tbody');
    tableBody.empty();

    togglePreloader();

    $('button.show-more-button').attr('onclick', 'showMore(' + 1 + ', \'' + currentUser + '\')');
    makeRequest('get', '/api/books/all', {page: 1}).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            let element = createBookElement(result[i].isn, result[i].title, result[i].author, result[i].user.name, result[i].id, currentUser);
            tableBody.append(element);
        }

        setTimeout(function () {
            togglePreloader();
        }, 1000);

    });
}

function reloadViewSort(orderBy, sortDir, currentUser) {
    let tableBody = $('tbody');
    tableBody.empty();

    togglePreloader();

    $('button.show-more-button').attr('onclick', 'showMore(' + 1 + ', \'' + currentUser + '\')');
    makeRequest('get', '/api/books/all', {page: 1, orderBy: orderBy, sortDir: sortDir}).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            let element = createBookElement(result[i].isn, result[i].title, result[i].author, result[i].user.name, result[i].id, currentUser);
            tableBody.append(element);
        }

        setTimeout(function () {
            togglePreloader();
        }, 1000);

    });
}

function createBookElement(isn, title, author, userName, id, currentUser) {
    let buttonTake = "<button class=\"mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored\" onclick=\"takeBook(" + id + ", '" + currentUser + "')\">Взять</button>";
    let buttonPut;
    if (userName === currentUser) {
        buttonPut = "<button class=\"mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored\" onclick=\"putBook(" + id + ")\">Вернуть</button>";
    } else {
        buttonPut = userName;
    }
    let element = `<tr>
                        <td class="mdl-data-table__cell--non-numeric" onclick="showEditModal(\'` + currentUser + `\', \'` + isn + `\', \'` + title + `\', \'` + author + `\', \'` + id + `\')">` + isn + `</td>
                        <td>` + title + `</td>
                        <td>` + author + `</td>
                        <td>` + (userName === null ? buttonTake : buttonPut) + `</td>
                        <td>
                            <button class="mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored" onclick="deleteBook(` + id + `);">
                                Удалить
                            </button>
                        </td>
                    </tr>`;

    return element;
}

function togglePreloader() {
    let tableElement = $('table');
    let preloaderElement = $('.cssload-thecube');

    let isActive = tableElement.css('display') === 'table';

    if (isActive) {
        tableElement.css('display', 'none');
        preloaderElement.css('display', 'block');
    } else {
        tableElement.css('display', 'table');
        preloaderElement.css('display', 'none');
    }
}