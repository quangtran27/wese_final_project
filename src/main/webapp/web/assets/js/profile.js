document.getElementById('user-img').onerror = (e) => {
    const defaultImgUrl = location.origin + '/web/assets/img/default-user-img.png';
    e.target.setAttribute('src', defaultImgUrl);
}