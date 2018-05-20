var wallTextBox;
var wallNameTitle;
var messageTextBox;
var messageNumberTextBox;
var messageTextArea;

function start() {
    wallTextBox = document.getElementById("wall_text_box");
    wallNameTitle = document.getElementById("wall_name_title");
    messageTextBox = document.getElementById("message_text_box");
    messageNumberTextBox = document.getElementById("message_number_text_box");
    messageTextArea = document.getElementById("message_text_area");
}

function setWallName() {
    if (wallTextBox.value !== "") {
        wallNameTitle.innerText = wallTextBox.value;
        wallTextBox.value = "";
    } else if (wallNameTitle.innerText !== "") {
        wallTextBox.value = "";
    }
    
    refreshMessageWall();
}


function refreshMessageWall() {
    if (wallNameTitle.innerText === "No active wall!") {
        return;
    }

    var httpRequest = new XMLHttpRequest();
    httpRequest.onload = function () {
        messageTextArea.value = this.responseText;
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.onerror = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.ontimeout = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.open("GET", "/walls/" + wallNameTitle.innerText, true);
    httpRequest.send();
    
}


function deleteMessageWall() {

    var httpRequest = new XMLHttpRequest();
    httpRequest.onload = function () {
        messageTextArea.value = "";
    };

    httpRequest.onerror = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.ontimeout = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.open("DELETE", "/walls/" + wallNameTitle.innerText, true);
    wallNameTitle.innerText = "No active wall!";
    httpRequest.send();
    setTimeout(refreshMessageWall, 100);
}

function postMessage() {
    if (messageTextBox.value === "") {
        window.alert("No message found");
        return;
    }

    var request = new XMLHttpRequest();
    request.open("POST", "/walls/"+ wallNameTitle.innerText, true);
    request.timeout = 5000;
    request.send(messageTextBox.value);
    messageTextBox.value = "";
    setTimeout(refreshMessageWall, 100);
}

function deleteMessage() {
    if (wallNameTitle.innerText === "") {
        return;
    }

    var httpRequest = new XMLHttpRequest();
    httpRequest.onload = function () {
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.onerror = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.ontimeout = function () {
        messageTextArea.value = "Error! Server not working!";
        setTimeout(refreshMessageWall, 100);
    };

    httpRequest.open("DELETE", "/walls/" + wallNameTitle.innerText + "/" + messageNumberTextBox.value, true);
    httpRequest.send();
    messageNumberTextBox.value = "";
    setTimeout(refreshMessageWall, 100);
}