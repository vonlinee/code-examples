
// doc --> https://developer.chrome.com/docs/extensions/reference/tabs/





chrome.runtime.onInstalled.addListener((reason) => {
    if (reason === chrome.runtime.OnInstalledReason.INSTALL) {
        console.log("plugin install")
        chrome.tabs.create({
            url: 'onboarding.html'
        });
    }
});


async function getCurrentTab() {
    let queryOptions = { 
        active: true, 
        currentWindow: true 
    };
    let [tab] = await chrome.tabs.query(queryOptions);
    return tab;
}


// try {
//     importScripts('background.js');
// } catch (error) {
//     console.error(error);
// }



// function showValidImages(data) {
//     chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
//         var tab = tabs[0];
//         chrome.cookies.getAll({
//             url: 'localhost',
//             name: 'openWinFocus'
//         }, function (Numcooki) {
//             sendMessageToContentScript({ cmd: 'test', value: 'Collection', 'cok': Numcooki }, function (response) {
//                 console.log('来自cont的回复：' + response);
//             });
//         });

//     });
// };

// chrome.contextMenus.create({
//     title: '右键菜单',
//     contexts: ['all'],
//     documentUrlPatterns: ['http://*/*', 'https://*/*'],
//     onclick: showValidImages
// });

// function sendMessageToContentScript(message, callback) {
//     chrome.tabs.query({ active: true, currentWindow: true }, function (tabs) {
//         chrome.tabs.sendMessage(tabs[0].id, message, function (response) {
//             if (callback) callback(response);
//         });
//     });
// }

// chrome.runtime.onMessage.addListener(function (request, sender, sendResponse) {
//     console.log(request);
//     sendResponse('get message！' + request.value);
//     return true;
// });