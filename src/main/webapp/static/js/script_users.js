function deleteUser(id) {
    if (confirm('Удалить пользователя?')) {
        makeRequest('get', '/api/users/delete/' + id, null).done(function (result) {
            if (result === 1) {
                alert("Пользователь успешно удалён");
                reloadView();
            } else {
                alert('Пользователь не был удален!');
            }
        });
    } else {
        alert('Пользователь не был удален!');
    }
}

function showModal() {
    let modalContent = `
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-name">
            <label class="mdl-textfield__label" for="modal-name">Введите Имя пользователя...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-login">
            <label class="mdl-textfield__label" for="modal-login">Введите Логин...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-password">
            <label class="mdl-textfield__label" for="modal-password">Введите Пароль...</label>
        </div>
    `;

    showDialog({
        id: 'dialog-id',
        title: 'Добавление пользователя',
        text: modalContent,
        negative: {
            id: 'cancel-button',
            title: 'Отменить',
            onClick: function () {
                alert("Пользователь не был добавлен")
            }
        },
        positive: {
            id: 'ok-button',
            title: 'Сохранить',
            onClick: function () {
                let postData = {
                    name: $('#modal-name').val(),
                    login: $('#modal-login').val(),
                    password: $('#modal-password').val()
                };

                makeRequest('post', '/api/users/add', postData).done(
                    function (result) {
                        if (result === 1) {
                            alert('Пользователь успешно добавлен');
                            reloadView();
                        } else {
                            alert('Ошибка!')
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

function prepareEditModal(id) {
    makeRequest('post', '/api/users/credentials', { id: id }).done(function (result) {
        showEditModal(result);
    });
}

function showEditModal(userAccount) {
    let modalContent = `
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-name" value="` + userAccount.user.name + `">
            <label class="mdl-textfield__label" for="modal-name">Введите Имя пользователя...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-login" value="` + userAccount.login + `">
            <label class="mdl-textfield__label" for="modal-login">Введите Логин...</label>
          </div>
        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
            <input class="mdl-textfield__input" type="text" id="modal-password" value="` + userAccount.password + `">
            <label class="mdl-textfield__label" for="modal-password">Введите Пароль...</label>
        </div>
    `;

    showDialog({
        id: 'dialog-id',
        title: 'Изменение пользователя',
        text: modalContent,
        negative: {
            id: 'cancel-button',
            title: 'Отменить',
            onClick: function () {
                alert("Пользователь не был изменен")
            }
        },
        positive: {
            id: 'ok-button',
            title: 'Сохранить',
            onClick: function () {
                let postData = {
                    name: $('#modal-name').val(),
                    login: $('#modal-login').val(),
                    password: $('#modal-password').val(),
                    id: userAccount.user.id
                };

                makeRequest('post', '/api/users/edit', postData).done(
                    function (result) {
                        if (result === 1) {
                            alert('Пользователь успешно изменен');
                            reloadView();
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

function makeRequest(type, url, data) {
    return $.ajax({
        url: '/library' + url,
        data: data,
        type: type,
        error: function (callback) {
            alert("Ошибка!");
        }
    });
}

function reloadView() {
    let tableBody = $('tbody');
    tableBody.empty();

    togglePreloader();

    makeRequest('get', '/api/users/all', null).done(function (result) {
        for (let i = 0; i < result.length; i++) {
            let element = createUserElement(result[i].name, result[i].id);
            tableBody.append(element);
        }

        setTimeout(function () {
            togglePreloader();
        }, 1000);

    });
}

function createUserElement(name, id) {
    let element = `<tr>
                        <td class="mdl-data-table__cell--non-numeric cursor-pointer" onclick="prepareEditModal('` + id + `')">` + name + `</td>
                        <td>
                            <button class="mdl-js-ripple-effect mdl-button mdl-js-button mdl-button--raised mdl-button--colored" onclick="deleteUser(` + id + `);">
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