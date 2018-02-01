function deleteBook(id) {
    if (confirm('Удалить книгу?')) {
        makeRequest('get', '/api/books/delete/' + id, null).done(function(result) {
            reloadView();
        });
    } else {
        alert('Книга не была удалена!');
    }
}

function showMore(page, currentUser) {
    $('button.show-more-button').attr('onclick', 'showMore(' + ++page + ', \'' + currentUser + '\')');

    togglePreloader();

    makeRequest('get', '/api/books/all', { page: page }).done(
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

    $('button.show-more-button').attr('onclick', 'showMore(' + 1 + ', ' + currentUser + ')');
    makeRequest('get', '/api/books/all', { page: 1 }).done(function (result) {
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
                        <td class="mdl-data-table__cell--non-numeric">` + isn + `</td>
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