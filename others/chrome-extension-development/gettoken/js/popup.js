// https://blog.csdn.net/foryouslgme/article/details/104362876





let content = document.getElementById("content");


chrome.storage.sync.get("cookie", ({ color }) => {

    chrome.cookies.getAll({
        url: 'https://www.baidu.com/',
        name: 'openWinFocus'
        //name是存储的cookie的name
    }, function (cookie_item) {
        content.innerHTML = (cookie_item);
    });
});


