if (Notification.permission === "granted") {
    Notification.requestPermission().then(function(result) {
        console.log(result);
    });
}

let eventSource = new EventSource("/api/post/notifications");
eventSource.addEventListener("message", function(e) {
    let post = JSON.parse(e.data);
    let notification = new Notification(post.title);
    notification.onclick = function() {
        window.location.href = "/" + post.slug;
    };
});
