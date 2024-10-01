const chatBox = document.getElementById("chatBox");
const userInputField = document.querySelector('input[name="userInput"]');
let userScrolling = false;

function scrollToBottom() {
    if (!userScrolling) {
        chatBox.scrollTop = chatBox.scrollHeight;
    }
}

window.onload = function() {
    scrollToBottom();
    userInputField.focus();
};

function typeWriter(text, element, speed) {
    let i = 0;
    userInputField.disabled = true;
    userInputField.blur();

    function typing() {
        if (i < text.length) {
            element.innerHTML += text.charAt(i);
            i++;
            setTimeout(typing, speed);
            scrollToBottom();
        } else {
            userInputField.disabled = false;
            userInputField.focus();
            scrollToBottom();
        }
    }
    typing();
}

// Enable user scrolling
function enableUserScroll() {
    userScrolling = true;
}

// Disable user scrolling
function disableUserScroll() {
    userScrolling = false;
    scrollToBottom();
}

chatBox.addEventListener('mousedown', enableUserScroll);
// chatBox.addEventListener('mouseup', disableUserScroll);
// chatBox.addEventListener('mouseleave', disableUserScroll);

document.querySelector('form').onsubmit = async function(event) {
    userScrolling = false;
    scrollToBottom();
    event.preventDefault();
    const userInput = userInputField.value;

    const userMessage = document.createElement("div");
    userMessage.className = "message-box user-message mb-3";
    userMessage.textContent = userInput;
    chatBox.appendChild(userMessage);

    userInputField.value = '';

    const botResponse = document.createElement("div");
    botResponse.className = "message-box bot-message mb-3";
    chatBox.appendChild(botResponse);

    const response = await fetch('/chat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({ userInput: userInput })
    });

    if (response.ok) {
        const html = await response.text();
        botResponse.innerHTML = '';
        const responseText = extractBotResponse(html);
        typeWriter(responseText, botResponse, 10);
    } else {
        botResponse.textContent = "Error fetching response.";
        userInputField.disabled = false;
        userInputField.focus();
    }
    scrollToBottom();
};

function extractBotResponse(html) {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');
    const messages = doc.querySelectorAll('.bot-message');
    return messages[messages.length - 1].textContent;
}
function setFormAction() {
    const form = document.getElementById('searchForm');
    form.setAttribute('action', '/admin/history/conversations/containing');
}