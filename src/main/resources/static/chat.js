// File: src/main/resources/static/chat.js
// Kết nối STOMP qua SockJS, subscribe và gửi message

(function () {
    const connectBtn = document.getElementById('connectBtn');
    const disconnectBtn = document.getElementById('disconnectBtn');
    const sendBtn = document.getElementById('sendBtn');
    const messagesEl = document.getElementById('messages');
    const messageInput = document.getElementById('message');
    const usernameInput = document.getElementById('username');

    let stompClient = null;
    let connected = false;

    function setUiConnected(connectedState) {
        connected = connectedState;
        connectBtn.disabled = connected;
        disconnectBtn.disabled = !connected;
        sendBtn.disabled = !connected;
    }

    function showMessage(msg, isMe) {
        const div = document.createElement('div');
        div.className = 'msg ' + (isMe ? 'me' : 'other');
        div.textContent = msg;
        messagesEl.appendChild(div);
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }

    function connect() {
        const username = usernameInput.value.trim() || 'Anonymous';
        const recipient = document.getElementById('recipient') ? document.getElementById('recipient').value : '';
        // SockJS endpoint must match server config `/ws`
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        // optional: disable debug logs
        stompClient.debug = null;

        stompClient.connect({}, function (frame) {
            setUiConnected(true);
            showMessage('Connected as ' + username, false);

            // load history first
            if (recipient) {
                loadHistory(recipient, username);
            }

            // Subscribe to user-specific queue for private messages
            stompClient.subscribe('/user/queue/messages', function (message) {
                const payload = JSON.parse(message.body);
                const text = formatMessageText(payload);
                showMessage(text, payload.sender === username);
            });
            // optional: subscribe to public topic
            stompClient.subscribe('/topic/public', function (message) {
                const payload = JSON.parse(message.body);
                const text = formatMessageText(payload);
                showMessage(text, payload.sender === username);
            });
        }, function (err) {
            setUiConnected(false);
            showMessage('Connection error', false);
            console.error('STOMP connect error', err);
        });
    }

    function formatTime(iso) {
        if (!iso) return '';
        try {
            const dt = new Date(iso);
            return dt.toLocaleString();
        } catch (e) {
            return iso;
        }
    }

    function formatMessageText(msg) {
        const time = formatTime(msg.createdAt || msg.created_at || msg.timestamp);
        return (time ? ('[' + time + '] ') : '') + msg.sender + ': ' + msg.content;
    }

    async function loadHistory(recipient, username) {
        try {
            const url = '/messages?with=' + encodeURIComponent(recipient);
            const res = await fetch(url, { credentials: 'same-origin' });
            if (!res.ok) {
                console.warn('Could not load history', res.status);
                return;
            }
            const messages = await res.json();
            // clear messages area
            messagesEl.innerHTML = '';
            messages.forEach(m => {
                const text = formatMessageText(m);
                showMessage(text, m.sender === username);
            });
        } catch (e) {
            console.error('Error loading message history', e);
        }
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setUiConnected(false);
        showMessage('Disconnected', false);
    }

    function sendMessage() {
        if (!stompClient || !connected) return;
        const content = messageInput.value.trim();
        if (!content) return;
        const payload = {
            sender: usernameInput.value.trim() || 'Anonymous',
            recipient: document.getElementById('recipient').value || '',
            content: content
        };
        // Destination matches controller mapping `/app/chat`
        stompClient.send('/app/chat', {}, JSON.stringify(payload));
        messageInput.value = '';
    }

    connectBtn.addEventListener('click', connect);
    disconnectBtn.addEventListener('click', disconnect);
    sendBtn.addEventListener('click', sendMessage);
    messageInput.addEventListener('keydown', function (e) {
        if (e.key === 'Enter') sendMessage();
    });

    // optional: auto-connect on load
    window.addEventListener('load', function () {
        // auto connect when page loads (chat page opens for a selected user)
        try { connect(); } catch (e) { console.error(e); }
    });
})();