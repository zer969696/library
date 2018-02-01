function deleteBook(id) {
    if (confirm('Удалить книгу?')) {
        makeRequest('get', '/api/books/delete/' + id, null).done(function(result) {
            reloadView();
        });
    } else {
        alert('Книга не была удалена!');
    }
}

function showMore(page) {
    $('button.show-more-button').attr('onclick', 'showMore(' + ++page + ')');

    togglePreloader();

    makeRequest('get', '/api/books/all', { page: page }).done(
        function (result) {
            let tableBody = $('tbody');

            for (let i = 0; i < result.length; i++) {
                let element = createBookElement(result[i].isn, result[i].title, result[i].author, result[i].user.name, result[i].id);
                tableBody.append(element);
            }

            setTimeout(function () {
                togglePreloader();
            }, 1000);
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

function reloadView() {
    let tableBody = $('tbody');
    tableBody.empty();

    togglePreloader();

    makeRequest('get', '/api/books/all/', null).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            let element = createBookElement(result[i].isn, result[i].title, result[i].author, result[i].user.name, result[i].id);
            tableBody.append(element);
        }

        setTimeout(function () {
            togglePreloader();
        }, 1000);

    });
}

function createBookElement(isn, title, author, userName, id) {
    let buttonTake = "<button class=\"mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored\">Взять</button>";
    let element = `<tr>
                        <td class="mdl-data-table__cell--non-numeric">` + isn + `</td>
                        <td>` + title + `</td>
                        <td>` + author + `</td>
                        <td>` + (userName === null ? buttonTake : userName) + `</td>
                        <td>
                            <button class="mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored" onclick="test(` + id + `);">
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