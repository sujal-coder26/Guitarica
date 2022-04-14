'use strict';
var welcomePage = document.querySelector('#welcome-page');
var globalChat = document.querySelector('#global-chat');
var formUsername = document.querySelector('#formUsername');
var formMessage = document.querySelector('#formMessage');
var messageInputBox = document.querySelector('#messageInputBox');
var areaMessage = document.querySelector('#areaMessage');
var connectingToChat = document.querySelector('.connectingToChat');
var rules = document.querySelector('.rulesChatBox');

var clientStomp = null;
var usernInChat = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connectToChatBox(event) {
    usernInChat = document.querySelector('#name').value.trim();
    if(usernInChat) {
        welcomePage.classList.add('hidden');
        globalChat.classList.remove('hidden');
        rules.classList.remove('hidden');

        var sockets = new SockJS('/chatBox');
        clientStomp = Stomp.over(sockets);

        clientStomp.connect({}, onConnectedWithChat, onErrorInJoining);
    }
    event.preventDefault();
}


function onConnectedWithChat() {
    // Subscribe to the Public Topic
    clientStomp.subscribe('/chatapp/global', messageReceived);

    // Tell your username to the server
    clientStomp.send("/webapp/chatform.userAdd",
        {},
        JSON.stringify({sender: usernInChat, type: 'JOINS'})
    )

    connectingToChat.classList.add('hidden');
    rules.classList.add('hidden');

}


function onErrorInJoining(error) {
    connectingToChat.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingToChat.style.color = 'red';
}

function messageSendInChat(event) {
    var messageContent = messageInputBox.value.trim();
    if(messageContent && clientStomp) {
        var chatMessage = {
            sender: usernInChat,
            content: messageInputBox.value,
            type: 'CHATS'
        };
        clientStomp.send("/webapp/chatform.messageSend", {}, JSON.stringify(chatMessage));
        messageInputBox.value = '';
    }
    event.preventDefault();
}


function messageReceived(payload) {
    var messageFromChat = JSON.parse(payload.body);

    var messageHTMLElement = document.createElement('li');

    if(messageFromChat.type === 'JOINS') {
        messageHTMLElement.classList.add('messageEvent');
        messageFromChat.content = messageFromChat.sender + ' joined!';
    } else if (messageFromChat.type === 'LEAVES') {
        messageHTMLElement.classList.add('messageEvent');
        messageFromChat.content = messageFromChat.sender + ' left!';
    } else {
        messageHTMLElement.classList.add('chatMessage');

        var avatarProfile = document.createElement('i');
        var avatarText = document.createTextNode(messageFromChat.sender[0]);
        avatarProfile.appendChild(avatarText);
        avatarProfile.style['background-color'] = getProfile(messageFromChat.sender);

        messageHTMLElement.appendChild(avatarProfile);

        var usernameInElement= document.createElement('span');
        var usernameInText = document.createTextNode(messageFromChat.sender);
        usernameInElement.appendChild(usernameInText);
        messageHTMLElement.appendChild(usernameInElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(messageFromChat.content);
    textElement.appendChild(messageText);

    messageHTMLElement.appendChild(textElement);

    areaMessage.appendChild(messageHTMLElement);
    areaMessage.scrollTop = areaMessage.scrollHeight;
}


function getProfile(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

formUsername.addEventListener('submit', connectToChatBox, true)
formMessage.addEventListener('submit', messageSendInChat, true)